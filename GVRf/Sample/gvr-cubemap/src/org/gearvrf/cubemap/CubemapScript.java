/* Copyright 2015 Samsung Electronics Co., LTD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gearvrf.cubemap;

import java.util.concurrent.Future;

import org.gearvrf.FutureWrapper;
import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRCubemapTexture;
import org.gearvrf.GVRMaterial;
import org.gearvrf.GVRMesh;
import org.gearvrf.GVRScene;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.GVRScript;
import org.gearvrf.GVRTexture;

import android.util.Log;

public class CubemapScript extends GVRScript {

    private static final float CUBE_WIDTH = 20.0f;
    private static final float SCALE_FACTOR = 2.0f;
    private GVRContext mGVRContext = null;

    @Override
    public void onInit(GVRContext gvrContext) {
        mGVRContext = gvrContext;

        GVRScene scene = mGVRContext.getNextMainScene();

        FutureWrapper<GVRMesh> futureQuadMesh = new FutureWrapper<GVRMesh>(
                gvrContext.createQuad(CUBE_WIDTH, CUBE_WIDTH));
        
//        String[] imageNames = new String[6];
//        imageNames[0] = "screenshot3d_0.png";
//        imageNames[1] = "screenshot3d_1.png";
//        imageNames[2] = "screenshot3d_2.png";
//        imageNames[3] = "screenshot3d_3.png";
//        imageNames[4] = "screenshot3d_5.png";
//        imageNames[5] = "screenshot3d_4.png";
//        GVRCubemapTexture.setFaceNames(imageNames);
//        Future<GVRTexture> futureCubemapTexture = gvrContext
//                .loadFutureCubemapTexture(new GVRAndroidResource(mGVRContext,
//                        R.raw.screenshot3d));
        Future<GVRTexture> futureCubemapTexture = gvrContext
                .loadFutureCubemapTexture(new GVRAndroidResource(mGVRContext,
                        R.raw.beach));

        GVRMaterial cubemapMaterial = new GVRMaterial(gvrContext,
                GVRMaterial.GVRShaderType.Cubemap.ID);
        cubemapMaterial.setMainTexture(futureCubemapTexture);

        // surrounding cube
        GVRSceneObject mFrontFace = new GVRSceneObject(gvrContext,
                futureQuadMesh, futureCubemapTexture);
        mFrontFace.getRenderData().setMaterial(cubemapMaterial);
        mFrontFace.setName("front");
        scene.addSceneObject(mFrontFace);
        mFrontFace.getTransform().setPosition(0.0f, 0.0f, -CUBE_WIDTH * 0.5f);

        GVRSceneObject backFace = new GVRSceneObject(gvrContext,
                futureQuadMesh, futureCubemapTexture);
        backFace.getRenderData().setMaterial(cubemapMaterial);
        backFace.setName("back");
        scene.addSceneObject(backFace);
        backFace.getTransform().setPosition(0.0f, 0.0f, CUBE_WIDTH * 0.5f);
        backFace.getTransform().rotateByAxis(180.0f, 0.0f, 1.0f, 0.0f);

        GVRSceneObject leftFace = new GVRSceneObject(gvrContext,
                futureQuadMesh, futureCubemapTexture);
        leftFace.getRenderData().setMaterial(cubemapMaterial);
        leftFace.setName("left");
        scene.addSceneObject(leftFace);
        leftFace.getTransform().setPosition(-CUBE_WIDTH * 0.5f, 0.0f, 0.0f);
        leftFace.getTransform().rotateByAxis(90.0f, 0.0f, 1.0f, 0.0f);

        GVRSceneObject rightFace = new GVRSceneObject(gvrContext,
                futureQuadMesh, futureCubemapTexture);
        rightFace.getRenderData().setMaterial(cubemapMaterial);
        rightFace.setName("right");
        scene.addSceneObject(rightFace);
        rightFace.getTransform().setPosition(CUBE_WIDTH * 0.5f, 0.0f, 0.0f);
        rightFace.getTransform().rotateByAxis(-90.0f, 0.0f, 1.0f, 0.0f);

        GVRSceneObject topFace = new GVRSceneObject(gvrContext, futureQuadMesh,
                futureCubemapTexture);
        topFace.getRenderData().setMaterial(cubemapMaterial);
        topFace.setName("top");
        scene.addSceneObject(topFace);
        topFace.getTransform().setPosition(0.0f, CUBE_WIDTH * 0.5f, 0.0f);
        topFace.getTransform().rotateByAxis(90.0f, 1.0f, 0.0f, 0.0f);

        GVRSceneObject bottomFace = new GVRSceneObject(gvrContext,
                futureQuadMesh, futureCubemapTexture);
        bottomFace.getRenderData().setMaterial(cubemapMaterial);
        bottomFace.setName("bottom");
        scene.addSceneObject(bottomFace);
        bottomFace.getTransform().setPosition(0.0f, -CUBE_WIDTH * 0.5f, 0.0f);
        bottomFace.getTransform().rotateByAxis(-90.0f, 1.0f, 0.0f, 0.0f);

        // reflective object
        Future<GVRMesh> futureSphereMesh = gvrContext
                .loadFutureMesh(new GVRAndroidResource(mGVRContext,
                        R.raw.sphere));
        GVRMaterial cubemapReflectionMaterial = new GVRMaterial(gvrContext,
                GVRMaterial.GVRShaderType.CubemapReflection.ID);
        cubemapReflectionMaterial.setMainTexture(futureCubemapTexture);

        GVRSceneObject sphere = new GVRSceneObject(gvrContext,
                futureSphereMesh, futureCubemapTexture);
        sphere.getRenderData().setMaterial(cubemapReflectionMaterial);
        sphere.setName("sphere");
        scene.addSceneObject(sphere);
        sphere.getTransform()
                .setScale(SCALE_FACTOR, SCALE_FACTOR, SCALE_FACTOR);
        sphere.getTransform().setPosition(0.0f, 0.0f, -CUBE_WIDTH * 0.25f);

        for (GVRSceneObject so : scene.getWholeSceneObjects()) {
            Log.v("", "scene object name : " + so.getName());
        }
    }

    @Override
    public void onStep() {
        FPSCounter.tick();
    }
}
