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

package org.gearvrf.sample.sceneobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import org.gearvrf.GVRActivity;
import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRCameraRig;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRMaterial;
import org.gearvrf.GVRRenderData;
import org.gearvrf.GVRScene;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.GVRScript;
import org.gearvrf.GVRTexture;
import org.gearvrf.animation.GVRAnimation;
import org.gearvrf.animation.GVRRepeatMode;
import org.gearvrf.animation.GVRRotationByAxisAnimation;
import org.gearvrf.scene_objects.GVRConeSceneObject;
import org.gearvrf.scene_objects.GVRCubeSceneObject;
import org.gearvrf.scene_objects.GVRCylinderSceneObject;
import org.gearvrf.scene_objects.GVRSphereSceneObject;

public class SampleViewManager extends GVRScript {
    private List<GVRSceneObject> objectList = new ArrayList<GVRSceneObject>();

    private int currentObject = 0;

    @Override
    public void onInit(GVRContext gvrContext) {

        GVRScene scene = gvrContext.getNextMainScene();

        // load texture asynchronously
        Future<GVRTexture> futureTexture = gvrContext
                .loadFutureTexture(new GVRAndroidResource(gvrContext,
                        R.drawable.gearvr_logo));

        // create a scene object (this constructor creates a rectangular scene
        // object that uses the standard 'unlit' shader)
        GVRSceneObject quadObject = new GVRSceneObject(gvrContext, 4.0f, 2.0f);
        GVRCubeSceneObject cubeObject = new GVRCubeSceneObject(gvrContext);
        GVRSphereSceneObject sphereObject = new GVRSphereSceneObject(gvrContext);
        GVRCylinderSceneObject cylinderObject = new GVRCylinderSceneObject(
                gvrContext);
        GVRConeSceneObject coneObject = new GVRConeSceneObject(gvrContext);

        objectList.add(quadObject);
        objectList.add(cubeObject);
        objectList.add(sphereObject);
        objectList.add(cylinderObject);
        objectList.add(coneObject);
        
        // turn all objects off, except the first one
        for(int i=1; i<objectList.size(); i++) {
            objectList.get(i).getRenderData().setRenderMask(0);
        }

        // setup material
        GVRMaterial material = new GVRMaterial(gvrContext);
        material.setMainTexture(futureTexture);

        quadObject.getRenderData().setMaterial(material);
        cubeObject.getRenderData().setMaterial(material);
        sphereObject.getRenderData().setMaterial(material);
        cylinderObject.getRenderData().setMaterial(material);
        coneObject.getRenderData().setMaterial(material);

        // set the scene object positions
        quadObject.getTransform().setPosition(0.0f, 0.0f, -3.0f);
        cubeObject.getTransform().setPosition(0.0f, -1.0f, -3.0f);
        cylinderObject.getTransform().setPosition(0.0f, 0.0f, -3.0f);
        coneObject.getTransform().setPosition(0.0f, 0.0f, -3.0f);
        sphereObject.getTransform().setPosition(0.0f, -1.0f, -3.0f);

        // GVRAnimation animation = new
        // GVRRotationByAxisAnimation(cylinderObject, 50, -3600, 0, 1, 0);
        // animation.setRepeatCount(GVRRepeatMode.REPEATED).setRepeatCount(-1);
        // animation.start(gvrContext.getAnimationEngine());
        // cubeObject.getTransform().setRotationByAxis(45.0f, 1.0f, 0.0f, 0.0f);

        // cylinderObject.getRenderData().setCullTest(false);

        // add the scene objects to the scene graph
        scene.addSceneObject(quadObject);
        scene.addSceneObject(cubeObject);
        scene.addSceneObject(sphereObject);
        scene.addSceneObject(cylinderObject);
        scene.addSceneObject(coneObject);

    }

    private float mYAngle = 0;
    private float mXAngle = 0;

    public void setYAngle(float angle) {
        mYAngle = angle;
    }

    public void setXAngle(float angle) {
        mXAngle = angle;
    }

    public void onTap() {

        objectList.get(currentObject).getRenderData().setRenderMask(0);

        currentObject++;
        int totalObjects = objectList.size();
        if (currentObject >= totalObjects) {
            currentObject = 0;
        }

        objectList
                .get(currentObject)
                .getRenderData()
                .setRenderMask(
                        GVRRenderData.GVRRenderMaskBit.Left
                                | GVRRenderData.GVRRenderMaskBit.Right);

    }

    @Override
    public void onStep() {
        objectList.get(currentObject).getTransform()
                .rotateByAxis(mXAngle, 1.0f, 0.0f, 0.0f);
        objectList.get(currentObject).getTransform()
                .rotateByAxis(mYAngle, 0.0f, 1.0f, 0.0f);
    }
}
