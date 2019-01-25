package com.buildworld.mods.core.states;

import com.buildworld.engine.audio.sound.SoundBuffer;
import com.buildworld.engine.audio.sound.SoundListener;
import com.buildworld.engine.audio.sound.SoundManager;
import com.buildworld.engine.audio.sound.SoundSource;
import com.buildworld.engine.graphics.Renderer;
import com.buildworld.engine.graphics.Window;
import com.buildworld.engine.graphics.camera.Camera;
import com.buildworld.engine.graphics.game.GameItem;
import com.buildworld.engine.graphics.game.Scene;
import com.buildworld.engine.graphics.game.SkyBox;
import com.buildworld.engine.graphics.game.Terrain;
import com.buildworld.engine.graphics.lights.DirectionalLight;
import com.buildworld.engine.graphics.lights.PointLight;
import com.buildworld.engine.graphics.lights.SceneLight;
import com.buildworld.engine.graphics.loaders.OBJLoader;
import com.buildworld.engine.graphics.loaders.assimp.StaticMeshesLoader;
import com.buildworld.engine.graphics.materials.Material;
import com.buildworld.engine.graphics.mesh.HeightMapMesh;
import com.buildworld.engine.graphics.mesh.Mesh;
import com.buildworld.engine.graphics.particles.FlowParticleEmitter;
import com.buildworld.engine.graphics.particles.Particle;
import com.buildworld.engine.graphics.textures.Texture;
import com.buildworld.engine.graphics.weather.Fog;
import com.buildworld.engine.io.MouseInput;
import com.buildworld.game.Game;
import com.buildworld.game.blocks.Block;
import com.buildworld.game.hud.Hud;
import com.buildworld.game.interactables.MouseBoxSelectionDetector;
import com.buildworld.game.state.State;
import com.buildworld.game.world.WorldController;
import com.buildworld.game.world.WorldState;
import com.buildworld.game.world.areas.Chunk;
import com.buildworld.game.world.areas.Galaxy;
import com.buildworld.game.world.areas.Region;
import com.buildworld.game.world.areas.World;
import com.buildworld.game.world.generators.Planet;
import com.buildworld.mods.core.world.biomes.DesertPlains;
import com.buildworld.mods.core.world.biomes.FrozenPlains;
import com.buildworld.mods.core.world.biomes.Plains;
import com.buildworld.mods.core.world.planets.Earth;
import de.matthiasmann.twl.utils.PNGDecoder;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.openal.AL11;

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

    private boolean sceneChanged;

    private final SoundManager soundMgr;

    private FlowParticleEmitter particleEmitter;

    private MouseBoxSelectionDetector selectDetector;

    private boolean leftButtonPressed;

    private boolean firstTime;


    private Vector2f currentCameraRegion = new Vector2f(0, 0);

    private static final float CAMERA_POS_STEP = 0.40f;

    private float angleInc;

    private float lightAngle;

    private final int viewDistance = 4 * 16;
    private final int loadDistance = viewDistance + 1 * 16;
    private final int dayLength = 120;

    private Vector3f camPos;
    private static final int DESIRED_RENDER_UPDATE_DELAY = 10;
    private int render_ticks = DESIRED_RENDER_UPDATE_DELAY;

    private Galaxy galaxy;
    private World world;
    private final int worldSize = 64;
    private int seed = 4242;

    private WorldController worldController;

    private Vector3f pointLightPos;


    private enum Sounds {
        FIRE
    };

    public GameStateTest() {
        renderer = new Renderer();
        hud = new Hud();
        soundMgr = new SoundManager();
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
        hud.init(window);
        renderer.init(window);
        soundMgr.init();

        leftButtonPressed = false;

        scene = new Scene();
        galaxy = new Galaxy();

        ///// THIS IS WHERE THE COMMENT BEGAN


//        float blockScale = 0.5f;
//        float skyBoxScale = 100.0f;
//        float extension = 2.0f;
//
//        float startx = extension * (-skyBoxScale + blockScale);
//        float startz = extension * (skyBoxScale - blockScale);
//        float starty = -1.0f;
//        float inc = blockScale * 2;
//
//        float posx = startx;
//        float posz = startz;
//        float incy = 0.0f;
//
//        PNGDecoder decoder = new PNGDecoder(new FileInputStream(Game.path + "/engine/resources/textures/heightmap.png"));
//        int height = decoder.getHeight();
//        int width = decoder.getWidth();
//        ByteBuffer buf = ByteBuffer.allocateDirect(4 * width * height);
//        decoder.decode(buf, width * 4, PNGDecoder.Format.RGBA);
//        buf.flip();
//
//        int instances = height * width;
//        Mesh mesh = OBJLoader.loadMesh(Game.path + "/engine/resources/models/cube.obj", instances);
//        Texture texture = new Texture(Game.path + "/engine/resources/textures/terrain_textures.png", 2, 1);
//        Material material = new Material(texture, reflectance);
//        mesh.setMaterial(material);
//        GameItem[] gameItems = new GameItem[instances];
//        for (int i = 0; i < height; i++) {
//            for (int j = 0; j < width; j++) {
//                GameItem gameItem = new GameItem(mesh);
//                gameItem.setScale(blockScale);
//                int rgb = HeightMapMesh.getRGB(i, j, width, buf);
//                incy = rgb / (10 * 255 * 255);
//                gameItem.setPosition(posx, starty + incy, posz);
//                int textPos = Math.random() > 0.5f ? 0 : 1;
//                gameItem.setTextPos(textPos);
//                gameItems[i * width + j] = gameItem;
//
//                posx += inc;
//            }
//            posx = startx;
//            posz -= inc;
//        }
//        scene.setGameItems(gameItems);
//
//        // Particles
//        int maxParticles = 200;
//        Vector3f particleSpeed = new Vector3f(0, 1, 0);
//        particleSpeed.mul(2.5f);
//        long ttl = 4000;
//        long creationPeriodMillis = 300;
//        float range = 0.2f;
//        float scale = 1.0f;
//        Mesh partMesh = OBJLoader.loadMesh(Game.path + "/engine/resources/models/particle.obj", maxParticles);
//        Texture particleTexture = new Texture(Game.path + "/engine/resources/textures/particle_anim.png", 4, 4);
//        Material partMaterial = new Material(particleTexture, reflectance);
//        partMesh.setMaterial(partMaterial);
//        Particle particle = new Particle(partMesh, particleSpeed, ttl, 100);
//        particle.setScale(scale);
//        particleEmitter = new FlowParticleEmitter(particle, maxParticles, creationPeriodMillis);
//        particleEmitter.setActive(true);
//        particleEmitter.setPositionRndRange(range);
//        particleEmitter.setSpeedRndRange(range);
//        particleEmitter.setAnimRange(10);
//        this.scene.setParticleEmitters(new FlowParticleEmitter[]{particleEmitter});


        Mesh[] houseMesh = StaticMeshesLoader.load(Game.path + "\\engine\\resources\\models\\house/house.obj", "\\engine\\resources\\models\\house");
        GameItem house = new GameItem(houseMesh);
        house.setPosition(0,32,0);
        scene.setGameItems(new GameItem[]{house});


        ///// THIS IS WHERE THE COMMENT ENDED

        // Shadows
        scene.setRenderShadows(true);

        // Fog
//        Vector3f fogColour = new Vector3f(0.5f, 0.5f, 0.5f);
//        scene.setFog(new Fog(true, fogColour, 0.02f));

        // Setup  SkyBox
//        SkyBox skyBox = new SkyBox(Game.path + "/engine/resources/models/skybox.obj", new Vector4f(0.65f, 0.65f, 0.65f, 1.0f));
//        skyBox.setScale(viewDistance);
//        scene.setSkyBox(skyBox);

        // Setup  SkyBox
        SkyBox skyBox = new SkyBox(Game.path + "\\engine\\resources/models/skybox.obj", "\\engine\\resources/textures/skybox.png");
        skyBox.setScale(viewDistance);
        scene.setSkyBox(skyBox);

        // Setup Lights
        setupLights();

        camera.getPosition().x = 0f;
        camera.getPosition().y = 64f;
        camera.getPosition().z = 0f;

        camera.getRotation().x = 25;
        camera.getRotation().y = -1;

        camPos = new Vector3f(camera.getPosition());

        // Sounds
        this.soundMgr.init();
        this.soundMgr.setAttenuationModel(AL11.AL_EXPONENT_DISTANCE);
        setupSounds();

    }

    public void generateWorld() throws Exception
    {
        world = new World(worldSize, WorldState.LOADED);
        world.setSeed(seed);
        Planet planet = new Earth(world.getSeed());
        WorldController worldController = new WorldController(world, planet);
        worldController.getBiomes().add(new Plains());
        worldController.getBiomes().add(new FrozenPlains());
        worldController.getBiomes().add(new DesertPlains());

        // Essentially loading a 3x3 square of regions around the origin 0,0
        //worldController.loadRegion(new Vector2f(-1,-1));
        //worldController.loadRegion(new Vector2f(-1,0));
        //worldController.loadRegion(new Vector2f(-1,1));
        //worldController.loadRegion(new Vector2f(0,-1));
        worldController.loadRegion(new Vector2f(0,0));
        //worldController.loadRegion(new Vector2f(0,1));
        //worldController.loadRegion(new Vector2f(1,-1));
        //worldController.loadRegion(new Vector2f(1,0));
        //worldController.loadRegion(new Vector2f(1,1));

        this.worldController = worldController;

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
        sceneLight.setDirectionalLight(directionalLight);

        pointLightPos = new Vector3f(0.0f, 25.0f, 0.0f);
        Vector3f pointLightColour = new Vector3f(0.0f, 1.0f, 0.0f);
        PointLight.Attenuation attenuation = new PointLight.Attenuation(1, 0.0f, 0);
        PointLight pointLight = new PointLight(pointLightColour, pointLightPos, lightIntensity, attenuation);
        sceneLight.setPointLightList( new PointLight[] {pointLight});
    }

    private void setupSounds() throws Exception {
        SoundBuffer buffFire = new SoundBuffer("\\engine\\resources/sounds/fire.ogg");
        soundMgr.addSoundBuffer(buffFire);
        SoundSource sourceFire = new SoundSource(true, false);
        Vector3f pos = new Vector3f(0, 72, 0);
        sourceFire.setPosition(pos);
        sourceFire.setBuffer(buffFire.getBufferId());
        soundMgr.addSoundSource(Sounds.FIRE.toString(), sourceFire);
        sourceFire.play();

        soundMgr.setListener(new SoundListener(new Vector3f(0, 0, 0)));
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {
        sceneChanged = false;
        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)) {
            sceneChanged = true;
            cameraInc.z = -1;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            sceneChanged = true;
            cameraInc.z = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            sceneChanged = true;
            cameraInc.x = -1;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            sceneChanged = true;
            cameraInc.x = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_Z)) {
            sceneChanged = true;
            cameraInc.y = -1;
        } else if (window.isKeyPressed(GLFW_KEY_X)) {
            sceneChanged = true;
            cameraInc.y = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            sceneChanged = true;
            angleInc -= 0.05f;
        } else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            sceneChanged = true;
            angleInc += 0.05f;
        } else {
            angleInc = 0;
        }
        if (window.isKeyPressed(GLFW_KEY_UP)) {
            sceneChanged = true;
            pointLightPos.y += 0.5f;
        } else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            sceneChanged = true;
            pointLightPos.y -= 0.5f;
        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput) throws Exception {
        scene.setGameItems(world.getUpdatedInRange((int)camPos.x, (int)camPos.y, (int)camPos.z, loadDistance));

        if (mouseInput.isRightButtonPressed()) {
            // Update camera based on mouse
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
            sceneChanged = true;
        }

        // Update camera position
        camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);

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

        // Update view matrix
        camera.updateViewMatrix();

        // Update sound listener position;
        soundMgr.updateListenerPosition(camera);

        boolean aux = mouseInput.isLeftButtonPressed();
        if (aux && !this.leftButtonPressed) {
            this.hud.incCounter();
        }
        this.leftButtonPressed = aux;

        // TODO: Coordinate calculations in List<Vector3f> spawn tons of vectors.

        if(render_ticks == 0)
        {
            int camX = (int)camera.getPosition().x, camZ= (int)camera.getPosition().z,  camY= (int)camera.getPosition().y;
            System.out.println("Cam coords: " + camX + ", " + camY + ", " + camZ);
            if(camX != (int)camPos.x || camZ != (int)camPos.z || camY != (int)camPos.y)
            {
                List<Vector3f> coords = world.getMovedRegionCoords((int)camPos.x, (int)camPos.y, (int)camPos.z,camX,camY,camZ, loadDistance);
                List<Vector3f> flipped = world.flipMovedRegionCoords(coords, (int)camPos.x,(int)camPos.y,(int)camPos.z, true,true, true);

                System.out.println("coords: " + coords.size());

                scene.removeGameItems(world.getMovedRegion(world.translateRegionCoords(flipped, new Vector3f(camX - (int)camPos.x, camZ - (int)camPos.y, camZ - (int)camPos.z))));
                List<Block> blockList = world.getMovedRegion(coords);
                System.out.println("RenderBlocks: " + blockList.size());
                if(blockList.size() != 0) {
                    Block block = blockList.get(0);
                    System.out.println("Block: x: " + block.getPosition().x + " y: " + block.getPosition().y + " z: " + block.getPosition().z);
                }
                scene.setGameItems(world.getMovedRegion(coords));
            }

            camPos.set(camX, camY, camZ);
        }

        boolean newRegion = false;
        Vector2f oldRegion = null;

        Vector2f regionMin = new Vector2f(currentCameraRegion.x * Region.size * Chunk.size, currentCameraRegion.y * Region.size * Chunk.size);
        Vector2f regionMax = new Vector2f((currentCameraRegion.x + 1) * Region.size * Chunk.size, (currentCameraRegion.y + 1) * Region.size * Chunk.size);

        if((int)camera.getPosition().x > regionMax.x){
            System.out.println("1");
            oldRegion = new Vector2f(this.currentCameraRegion);
            currentCameraRegion.x++;
            newRegion = true;
        }
        if((int)camera.getPosition().x < regionMin.x){
            System.out.println("2");
            oldRegion = new Vector2f(this.currentCameraRegion);
            currentCameraRegion.x--;
            newRegion = true;
        }
        if((int)camera.getPosition().z > regionMax.y){
            System.out.println("3");
            oldRegion = new Vector2f(this.currentCameraRegion);
            currentCameraRegion.y++;
            newRegion = true;
        }
        if((int)camera.getPosition().z < regionMin.y){
            System.out.println("4");
            oldRegion = new Vector2f(this.currentCameraRegion);
            currentCameraRegion.y--;
            newRegion = true;
        }

        if(newRegion){
            System.out.println("Loading new region..." + this.currentCameraRegion.x + " " + this.currentCameraRegion.y);
            if(!worldController.isRegionLoaded(oldRegion)) {
                worldController.unloadRegion(oldRegion);
            }
            worldController.loadRegion(this.currentCameraRegion);
        }

        render_ticks--;
        if(render_ticks < 0)
            render_ticks = DESIRED_RENDER_UPDATE_DELAY;

//        particleEmitter.update((long) (interval * 1000));
    }

    @Override
    public void render(Window window) {
        if (firstTime) {
            sceneChanged = true;
            firstTime = false;
        }
        renderer.render(window, camera, scene, sceneChanged);
        hud.render(window);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        soundMgr.cleanup();

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