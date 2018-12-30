package com.buildworld.mods.core.states;

import com.buildworld.engine.graphics.Camera;
import com.buildworld.engine.graphics.Renderer;
import com.buildworld.engine.graphics.Window;
import com.buildworld.engine.graphics.game.GameItem;
import com.buildworld.engine.graphics.game.Scene;
import com.buildworld.engine.graphics.game.SkyBox;
import com.buildworld.engine.graphics.lights.DirectionalLight;
import com.buildworld.engine.graphics.lights.SceneLight;
import com.buildworld.engine.graphics.mesh.Mesh;
import com.buildworld.engine.io.MouseInput;
import com.buildworld.engine.utils.SimplexNoise;
import com.buildworld.game.hud.Hud;
import com.buildworld.game.state.State;
import com.buildworld.game.world.World;
import com.buildworld.mods.core.blocks.Grass;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;

public class GameState implements State {

    private static final float MOUSE_SENSITIVITY = 0.2f;

    private final Vector3f cameraInc;

    private final Renderer renderer;

    private final Camera camera;

    private Scene scene;

    private Hud hud;

    private float lightAngle;

    private static final float CAMERA_POS_STEP = 0.6f;

    private final int viewDistance = 2 * 16;
    private final int loadDistance = viewDistance + 1 * 16;
    private final int dayLength = 120;

    private int camXOld, camZOld;
    private static final int DESIRED_RENDER_UPDATE_DELAY = 40;
    private int render_ticks = DESIRED_RENDER_UPDATE_DELAY;

    private World world;
    private final int worldSize = 64;
    public final int worldHeight = 256;
    private final boolean generateSurfaceLayerOnly = true;

    public GameState() {
        renderer = new Renderer();
        camera = new Camera();
        cameraInc = new Vector3f(0.0f, 0.0f, 0.0f);
        lightAngle = -90;
    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);

        scene = new Scene();

        world = new World();

        float skyBoxScale = viewDistance;

        camera.getPosition().x = 0f;
        camera.getPosition().y = 32f;
        camera.getPosition().z = 0f;
        camXOld = 0;
        camZOld = 0;

        //scene.setGameItems(world.getRegion((int)camera.getPosition().x, (int)camera.getPosition().y, (int)camera.getPosition().z, loadDistance));

        // Setup  SkyBox
        SkyBox skyBox = new SkyBox("C:\\Users\\using\\Desktop\\shawn\\build-world\\engine\\resources/models/skybox.obj", "C:\\Users\\using\\Desktop\\shawn\\build-world\\engine\\resources/textures/skybox.png");
        skyBox.setScale(skyBoxScale);
        scene.setSkyBox(skyBox);

        // Setup Lights
        setupLights();

        // Create HUD
        hud = new Hud("DEMO");
    }

    public void generateWorld() throws Exception
    {
        int dimension = worldSize * 16;

        long rng = new Random().nextLong();
        System.out.println("Seed: " + rng);
        SimplexNoise noise = new SimplexNoise(rng);

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int height = (int) ((noise.eval(i / (float) worldSize, j / (float) worldSize) + 1f) * 16f);
                for (int w = 0; w < height; w++) {
                    if (generateSurfaceLayerOnly && w != height - 1)
                        continue;
                    int x = i - (dimension / 2);
                    int y = w;
                    int z = j - (dimension / 2);
                    world.setBlock(x, y, z, new Grass());
                }
            }
        }

    }

    private void setupLights() {
        SceneLight sceneLight = new SceneLight();
        scene.setSceneLight(sceneLight);

        // Ambient Light
        sceneLight.setAmbientLight(new Vector3f(1.0f, 1.0f, 1.0f));

        // Directional Light
        /*
         * Dawn: (-1, 0, 0)
         *
         * Mid day: (0, 1, 0)
         *
         * Dusk: (1, 0, 0)
         */
        float lightIntensity = 1.0f;
        Vector3f lightPosition = new Vector3f(0, 1, 0);
        sceneLight.setDirectionalLight(new DirectionalLight(new Vector3f(1, 1, 1), lightPosition, lightIntensity));
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
    }

    @Override
    public void update(float interval, MouseInput mouseInput) throws Exception {

        scene.setGameItems(world.getUpdatedInRange(camXOld, camZOld, loadDistance));

        // Update camera based on mouse
        if (mouseInput.isRightButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);

            // Update HUD compass
            hud.rotateCompass(camera.getRotation().y);
        }

        // Update camera position
        camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);

        int ticksToDusk = 40 * dayLength;
        lightAngle += (180f / ticksToDusk);

        if(render_ticks % 2 == 0) {
            SceneLight sceneLight = scene.getSceneLight();

            // Update directional light direction, intensity and colour
            DirectionalLight directionalLight = sceneLight.getDirectionalLight();
            if (lightAngle > 90) {
                directionalLight.setIntensity(0);
                if (lightAngle >= 360) {
                    lightAngle = -90;
                }
                sceneLight.getAmbientLight().set(0.3f, 0.3f, 0.4f);
            } else if (lightAngle <= -80 || lightAngle >= 80) {
                float factor = 1 - (float) (Math.abs(lightAngle) - 80) / 10.0f;
                sceneLight.getAmbientLight().set(factor, factor, factor);
                directionalLight.setIntensity(factor);
                directionalLight.getColor().y = Math.max(factor, 0.9f);
                directionalLight.getColor().z = Math.max(factor, 0.5f);
            } else {
                sceneLight.getAmbientLight().set(1, 1, 1);
                directionalLight.setIntensity(1);
                directionalLight.getColor().x = 1;
                directionalLight.getColor().y = 1;
                directionalLight.getColor().z = 1;
            }
            double angRad = Math.toRadians(lightAngle);
            directionalLight.getDirection().x = (float) Math.sin(angRad);
            directionalLight.getDirection().y = (float) Math.cos(angRad);
        }

        if(render_ticks == 0)
        {
            int camX = (int)camera.getPosition().x, camZ= (int)camera.getPosition().z;
            if(camX != camXOld || camZ != camZOld)
            {
                Vector2f[] coords = world.getMovedRegionCoords(camXOld,camZOld,camX,camZ, loadDistance);
                Vector2f[] flipped = world.flipMovedRegionCoords(coords, camXOld,camZOld,loadDistance, true, true);

                scene.removeGameItems(world.getMovedRegion(world.translateRegionCoords(flipped, new Vector2f(camX - camXOld, camZ - camZOld)), 0, worldHeight));
                scene.setGameItems(world.getMovedRegion(coords, 0, worldHeight));
            }

            camXOld = camX;
            camZOld = camZ;
        }

        render_ticks--;
        if(render_ticks < 0)
            render_ticks = DESIRED_RENDER_UPDATE_DELAY;
    }

    @Override
    public void render(Window window) {
        hud.updateSize(window);
        renderer.render(window, camera, scene, hud);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        Map<Mesh, List<GameItem>> mapMeshes = scene.getGameMeshes();
        for (Mesh mesh : mapMeshes.keySet()) {
            mesh.cleanUp();
        }
        hud.cleanup();
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
}