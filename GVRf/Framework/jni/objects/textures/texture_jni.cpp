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


/***************************************************************************
 * JNI
 ***************************************************************************/

#include "texture.h"

#include "util/gvr_jni.h"

namespace gvr {
extern "C" {
JNIEXPORT jint JNICALL
Java_org_gearvrf_NativeTexture_getId(JNIEnv * env, jobject obj,
        jlong jtexture);
}
;

JNIEXPORT jint JNICALL
Java_org_gearvrf_NativeTexture_getId(JNIEnv * env, jobject obj,
        jlong jtexture) {
    std::shared_ptr<Texture> texture =
            *reinterpret_cast<std::shared_ptr<Texture>*>(jtexture);
    return texture->getId();
}

}
