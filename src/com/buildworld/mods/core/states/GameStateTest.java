package com.buildworld.mods.core.states;

import com.buildworld.engine.graphics.Camera;
import com.buildworld.engine.graphics.Renderer;
import com.buildworld.engine.graphics.Window;
import com.buildworld.engine.graphics.game.GameItem;
import com.buildworld.engine.graphics.game.Scene;
import com.buildworld.engine.graphics.game.SkyBox;
import com.buildworld.engine.graphics.game.Terrain;
import com.buildworld.engine.graphics.lights.DirectionalLight;
import com.buildworld.engine.graphics.lights.SceneLight;
import com.buildworld.engine.graphics.loaders.OBJLoader;
import com.buildworld.engine.graphics.materials.Material;
import com.buildworld.engine.graphics.mesh.HeightMapMesh;
import com.buildworld.engine.graphics.mesh.Mesh;
import com.buildworld.engine.graphics.particles.FlowParticleEmitter;
import com.buildworld.engine.graphics.particles.Particle;
import com.buildworld.engine.graphics.textures.Texture;
import com.buildworld.engine.graphics.weather.Fog;
import com.buildworld.engine.io.MouseInput;
import com.buildworld.game.Game;
import com.buildworld.game.hud.Hud;
import com.buildworld.game.state.State;
import com.buildworld.game.world.WorldController;
import com.buildworld.game.world.WorldState;
import com.buildworld.game.world.areas.Galaxy;
import com.buildworld.game.world.areas.World;
import com.buildworld.game.world.generators.Planet;
import com.buildworld.mods.core.world.biomes.Plains;
import com.buildworld.mods.core.world.planets.Earth;
import de.matthiasmann.twl.utils.PNGDecoder;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;

public class GameStateTest implements State {

    private static final float MOUSE_SENSITIVITY = 0.2f;

    private final Vector3f cameraInc;

    private final Renderer renderer;

    private final Camera camera;

    private Scene scene;

    private Hud hud;

    private static final float CAMERA_POS_STEP = 0.10f;

    private float angleInc;

    private float lightAngle;

    private FlowParticleEmitter particleEmitter;

    private final int viewDistance = 4 * 16;
    private final int loadDistance = viewDistance + 1 * 16;
    private final int dayLength = 120;

    private Vector3f camPos;
    private static final int DESIRED_RENDER_UPDATE_DELAY = 10;
    private int render_ticks = DESIRED_RENDER_UPDATE_DELAY;

    private Galaxy galaxy;
    private World world;
    private final int worldSize = 64;
    private int seed = 42;


    public GameStateTest() {
        renderer = new Renderer();
        camera = new Camera();
        cameraInc = new Vector3f(0.0f, 0.0f, 0.0f);
        angleInc = 0;
        lightAngle = 45;
    }

    public int getViewDistance() {
        return viewDistance;
    }

    public int getLoadDistance() {
        return loadDistance;
    }

    public int getDayLength() {
        return dayLength;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);

        scene = new Scene();
        galaxy = new Galaxy();

        float reflectance = 1f;

        ///// THIS IS WHERE THE COMMENT BEGAN


        float blockScale = 0.5f;
        float skyBoxScale = 100.0f;
        float extension = 2.0f;

        float startx = extension * (-skyBoxScale + blockScale);
        float startz = extension * (skyBoxScale - blockScale);
        float starty = -1.0f;
        float inc = blockScale * 2;

        float posx = startx;
        float posz = startz;
        float incy = 0.0f;

        PNGDecoder decoder = new PNGDecoder(new FileInputStream(Game.path + "/engine/resources/textures/heightmap.png"));
        int height = decoder.getHeight();
        int width = decoder.getWidth();
        ByteBuffer buf = ByteBuffer.allocateDirect(4 * width * height);
        decoder.decode(buf, width * 4, PNGDecoder.Format.RGBA);
        buf.flip();

        int instances = height * width;
        Mesh mesh = OBJLoader.loadMesh(Game.path + "/engine/resources/models/cube.obj", instances);
        Texture texture = new Texture(Game.path + "/engine/resources/textures/terrain_textures.png", 2, 1);
        Material material = new Material(texture, reflectance);
        mesh.setMaterial(material);
        GameItem[] gameItems = new GameItem[instances];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                GameItem gameItem = new GameItem(mesh);
                gameItem.setScale(blockScale);
                int rgb = HeightMapMesh.getRGB(i, j, width, buf);
                incy = rgb / (10 * 255 * 255);
                gameItem.setPosition(posx, starty + incy, posz);
                int textPos = Math.random() > 0.5f ? 0 : 1;
                gameItem.setTextPos(textPos);
                gameItems[i * width + j] = gameItem;

                posx += inc;
            }
            posx = startx;
            posz -= inc;
        }
        scene.setGameItems(gameItems);

        // Particles
        int maxParticles = 200;
        Vector3f particleSpeed = new Vector3f(0, 1, 0);
        particleSpeed.mul(2.5f);
        long ttl = 4000;
        long creationPeriodMillis = 300;
        float range = 0.2f;
        float scale = 1.0f;
        Mesh partMesh = OBJLoader.loadMesh(Game.path + "/engine/resources/models/particle.obj", maxParticles);
        Texture particleTexture = new Texture(Game.path + "/engine/resources/textures/particle_anim.png", 4, 4);
        Material partMaterial = new Material(particleTexture, reflectance);
        partMesh.setMaterial(partMaterial);
        Particle particle = new Particle(partMesh, particleSpeed, ttl, 100);
        particle.setScale(scale);
        particleEmitter = new FlowParticleEmitter(particle, maxParticles, creationPeriodMillis);
        particleEmitter.setActive(true);
        particleEmitter.setPositionRndRange(range);
        particleEmitter.setSpeedRndRange(range);
        particleEmitter.setAnimRange(10);
        this.scene.setParticleEmitters(new FlowParticleEmitter[]{particleEmitter});


        ///// THIS IS WHERE THE COMMENT ENDED

        // Shadows
        scene.setRenderShadows(true);

        // Fog
        Vector3f fogColour = new Vector3f(0.5f, 0.5f, 0.5f);
        scene.setFog(new Fog(true, fogColour, 0.02f));

        // Setup  SkyBox
//        SkyBox skyBox = new SkyBox(Game.path + "/engine/resources/models/skybox.obj", new Vector4f(0.65f, 0.65f, 0.65f, 1.0f));
//        skyBox.setScale(viewDistance);
//        scene.setSkyBox(skyBox);

        // Setup  SkyBox
        SkyBox skyBox = new SkyBox(Game.path + "\\engine\\resources/models/skybox.obj", Game.path + "\\engine\\resources/textures/skybox.png");
        skyBox.setScale(viewDistance);
        scene.setSkyBox(skyBox);

        // Setup Lights
        setupLights();

//        camera.getPosition().x = 0f;
//        camera.getPosition().y = 150f;
//        camera.getPosition().z = 0f;

        camera.getRotation().x = 25;
        camera.getRotation().y = -1;

        camPos = new Vector3f(camera.getPosition());

        hud = new Hud("BUILD WORLD");

    }

    public void generateWorld() throws Exception
    {
        world = new World(worldSize, WorldState.LOADED);
        world.setSeed(seed);
        Planet planet = new Earth(world.getSeed());
        WorldController worldController = new WorldController(world, planet);
        worldController.setBiome(new Plains());

        // Essentially loading a 3x3 square of regions around the origin 0,0
//        worldController.loadRegion(new Vector2f(-1,-1));
//        worldController.loadRegion(new Vector2f(-1,0));
//        worldController.loadRegion(new Vector2f(-1,1));
//        worldController.loadRegion(new Vector2f(0,-1));
        worldController.loadRegion(new Vector2f(0,0));
//        worldController.loadRegion(new Vector2f(0,1));
//        worldController.loadRegion(new Vector2f(1,-1));
//        worldController.loadRegion(new Vector2f(1,0));
//        worldController.loadRegion(new Vector2f(1,1));

        scene.setGameItems(world.getRegion((int)camera.getPosition().x, (int)camera.getPosition().y, (int)camera.getPosition().z, viewDistance));
        world.getAdded().clear();
    }

    private void setupLights() {
        SceneLight sceneLight = new SceneLight();
        scene.setSceneLight(sceneLight);

        // Ambient Light
        sceneLight.setAmbientLight(new Vector3f(0.3f, 0.3f, 0.3f));
        sceneLight.setSkyBoxLight(new Vector3f(1.0f, 1.0f, 1.0f));

        // Directional Light
        float lightIntensity = 1.0f;
        Vector3f lightDirection = new Vector3f(0, 1, 1);
        DirectionalLight directionalLight = new DirectionalLight(new Vector3f(1, 1, 1), lightDirection, lightIntensity);
        directionalLight.setShadowPosMult(10);
        directionalLight.setOrthoCords(-10.0f, 10.0f, -10.0f, 10.0f, -1.0f, 20.0f);
        sceneLight.setDirectionalLight(directionalLight);
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {
        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -1;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -1;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_Z)) {
            cameraInc.y = -1;
        } else if (window.isKeyPressed(GLFW_KEY_X)) {
            cameraInc.y = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            angleInc -= 0.05f;
        } else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            angleInc += 0.05f;
        } else {
            angleInc = 0;
        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput) throws Exception {
//        scene.setGameItems(world.getUpdatedInRange((int)camPos.x, (int)camPos.y, (int)camPos.z, loadDistance));

        // Update camera based on mouse
        if (mouseInput.isRightButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
        }

        // Update camera position
        Vector3f prevPos = new Vector3f(camera.getPosition());
        camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);
        // Check if there has been a collision. If true, set the y position to
        // the maximum height
//        float height = terrain != null ? terrain.getHeight(camera.getPosition()) : -Float.MAX_VALUE;
//        if (camera.getPosition().y <= height) {
//            camera.setPosition(prevPos.x, prevPos.y, prevPos.z);
//        }

        lightAngle += angleInc;
        if (lightAngle < 0) {
            lightAngle = 0;
        } else if (lightAngle > 180) {
            lightAngle = 180;
        }
        float zValue = (float) Math.cos(Math.toRadians(lightAngle));
        float yValue = (float) Math.sin(Math.toRadians(lightAngle));
        Vector3f lightDirection = this.scene.getSceneLight().getDirectionalLight().getDirection();
        lightDirection.x = 0;
        lightDirection.y = yValue;
        lightDirection.z = zValue;
        lightDirection.normalize();

//        if(render_ticks == 0)
//        {
//            int camX = (int)camera.getPosition().x, camZ= (int)camera.getPosition().z, camY = (int)camera.getPosition().y;
//            System.out.println("Cam coords: " + camX + ", " + camY + ", " + camZ);
//            if(camX != (int)camPos.x || camZ != (int)camPos.z || camY != (int)camPos.y)
//            {
//                List<Vector3f> coords = world.getMovedRegionCoords((int)camPos.x, (int)camPos.y, (int)camPos.z,camX,camY,camZ, loadDistance);
//                List<Vector3f> flipped = world.flipMovedRegionCoords(coords, (int)camPos.x,(int)camPos.y,(int)camPos.z, true,true, true);
//
//                System.out.println("coords: " + coords.size());
//
//                scene.removeGameItems(world.getMovedRegion(world.translateRegionCoords(flipped, new Vector3f(camX - (int)camPos.x, camZ - (int)camPos.y, camZ - (int)camPos.z))));
//                scene.setGameItems(world.getMovedRegion(coords));
//            }
//
//            camPos.set(camX, camY, camZ);
//        }

        render_ticks--;
        if(render_ticks < 0)
            render_ticks = DESIRED_RENDER_UPDATE_DELAY;

//        particleEmitter.update((long) (interval * 1000));
    }

    @Override
    public void render(Window window) {
        if (hud != null) {
            hud.updateSize(window);
        }
        renderer.render(window, camera, scene, hud);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        scene.cleanup();
        if (hud != null) {
            hud.cleanup();
        }
    }

    @Override
    public void load() {

    }

    @Override
    public void ready() {

    }

    @Override
    public void enter() {

    }

    @Override
    public void exit() {

    }
}