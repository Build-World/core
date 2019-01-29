package com.buildworld.mods.core.states;

import com.buildworld.engine.graphics.Renderer;
import com.buildworld.engine.graphics.Window;
import com.buildworld.engine.graphics.camera.Camera;
import com.buildworld.engine.graphics.game.Scene;
import com.buildworld.engine.graphics.game.SkyBox;
import com.buildworld.engine.graphics.lights.DirectionalLight;
import com.buildworld.engine.graphics.lights.SceneLight;
import com.buildworld.engine.graphics.mesh.Mesh;
import com.buildworld.engine.io.MouseInput;
import com.buildworld.game.Game;
import com.buildworld.game.blocks.Block;
import com.buildworld.game.hud.Hud;
import com.buildworld.game.state.State;
import com.buildworld.game.world.WorldController;
import com.buildworld.game.world.WorldState;
import com.buildworld.game.world.areas.Galaxy;
import com.buildworld.game.world.areas.World;
import com.buildworld.game.world.generators.Planet;
import com.buildworld.mods.core.blocks.Grass;
import com.buildworld.mods.core.world.biomes.Plains;
import com.buildworld.mods.core.world.planets.Earth;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.*;

import static org.lwjgl.glfw.GLFW.*;

public class GameState implements State {

    private static final float MOUSE_SENSITIVITY = 0.2f;

    private final Vector3f cameraInc;

    private final Renderer renderer;

    private final Camera camera;

    private Scene scene;

    private Hud hud;

    private float lightAngle;

    private boolean sceneChanged = false;

    private static final float CAMERA_POS_STEP = 0.6f;

    private final int viewDistance = 2 * 16;
    private final int loadDistance = viewDistance + 1 * 16;
    private final int dayLength = 120;

    private Vector3f camPos;
    private static final int DESIRED_RENDER_UPDATE_DELAY = 10;
    private int render_ticks = DESIRED_RENDER_UPDATE_DELAY;

    private Galaxy galaxy;
    private World world;
    private final int worldSize = 64;
    private int seed = 42;


    public GameState() {
        renderer = new Renderer();
        camera = new Camera();
        cameraInc = new Vector3f(0.0f, 0.0f, 0.0f);
        camPos = new Vector3f(0,0,0);
        lightAngle = -90;
    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);

        scene = new Scene();

        galaxy = new Galaxy();

        float skyBoxScale = viewDistance;

        camera.getPosition().x = 0f;
        camera.getPosition().y = 150f;
        camera.getPosition().z = 0f;
        camPos = new Vector3f(camera.getPosition());

        //scene.setGameItems(world.getRegion((int)camera.getPosition().x, (int)camera.getPosition().y, (int)camera.getPosition().z, loadDistance));

        // Setup  SkyBox
        SkyBox skyBox = new SkyBox(Game.path + "\\engine\\resources/models/skybox.obj", Game.path + "\\engine\\resources/textures/skybox.png");
        skyBox.setScale(skyBoxScale);
        scene.setSkyBox(skyBox);

        // Setup Lights
        setupLights();

        // Create HUD
        hud = new Hud();
        hud.init(window);
    }

    public void generateWorld() throws Exception
    {

        world = new World(worldSize, WorldState.LOADED);
        world.setSeed(seed);
        Planet planet = new Earth(world.getSeed());
        WorldController worldController = new WorldController(world, planet);
        worldController.getBiomes().add(new Plains());

        // Essentially loading a 3x3 square of regions around the origin 0,0
        /*worldController.loadRegion(new Vector2f(-1,-1));
        worldController.loadRegion(new Vector2f(-1,0));
        worldController.loadRegion(new Vector2f(-1,1));
        worldController.loadRegion(new Vector2f(0,-1));*/
        worldController.loadRegion(new Vector2f(0,0));
        /*worldController.loadRegion(new Vector2f(0,1));
        worldController.loadRegion(new Vector2f(1,-1));
        worldController.loadRegion(new Vector2f(1,0));
        worldController.loadRegion(new Vector2f(1,1));*/

//        scene.setGameItems(world.getRegion((int)camera.getPosition().x, (int)camera.getPosition().y, (int)camera.getPosition().z, viewDistance));
        world.getAdded().clear();
        //scene.setGameItems(world.getUpdatedInRange((int)camera.getPosition().x, (int)camera.getPosition().y, (int)camera.getPosition().z, viewDistance));
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

//        scene.setGameItems(world.getUpdatedInRange((int)camPos.x, (int)camPos.y, (int)camPos.z, loadDistance));

        // Update camera based on mouse
        if (mouseInput.isRightButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);

            // Update HUD compass
//            hud.rotateCompass(camera.getRotation().y);
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
            int camX = (int)camera.getPosition().x, camZ= (int)camera.getPosition().z, camY = (int)camera.getPosition().y;
            System.out.println("Cam coords: " + camX + ", " + camY + ", " + camZ);
            if(camX != (int)camPos.x || camZ != (int)camPos.z || camY != (int)camPos.y)
            {
                List<Vector3f> coords = world.getMovedRegionCoords((int)camPos.x, (int)camPos.y, (int)camPos.z,camX,camY,camZ, loadDistance);
                List<Vector3f> flipped = world.flipMovedRegionCoords(coords, (int)camPos.x,(int)camPos.y,(int)camPos.z, true,true, true);

                System.out.println("coords: " + coords.size());
//
//                scene.removeGameItems(world.getMovedRegion(world.translateRegionCoords(flipped, new Vector3f(camX - (int)camPos.x, camZ - (int)camPos.y, camZ - (int)camPos.z))));
//                scene.setGameItems(world.getMovedRegion(coords));
            }

            camPos.set(camX, camY, camZ);
        }

        render_ticks--;
        if(render_ticks < 0)
            render_ticks = DESIRED_RENDER_UPDATE_DELAY;
    }

    @Override
    public void render(Window window) {
        hud.render(window);
        renderer.render(window, camera, scene, this.sceneChanged);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
//        Map<Mesh, List<GameItem>> mapMeshes = scene.getGameMeshes();
//        for (Mesh mesh : mapMeshes.keySet()) {
//            mesh.cleanUp();
//        }
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