LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE    :=jpegbither
LOCAL_SRC_FILES :=libjpegbither.so
include $(PREBUILT_SHARED_LIBRARY)
include $(CLEAR_VARS)
LOCAL_MODULE    :=optimization
LOCAL_SRC_FILES :=JniUtil.cpp
LOCAL_SHARED_LIBRARIES :=jpegbither
LOCAL_LDLIBS := -ljnigraphics -llog
include $(BUILD_SHARED_LIBRARY)