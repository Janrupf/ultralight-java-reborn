###############
# CMake setup #
###############
cmake_minimum_required(VERSION 3.26)
project(ultralight-java-reborn)

#####################
# Set CMake options #
#####################
set(CMAKE_CXX_STANDARD 20)

if(NOT DEFINED UJR_JNI_HEADER_DIR)
    # Usually set via gradle, but CMake can also be invoked manually
    message(SEND_ERROR "Missing UJR_JNI_HEADER_DIR, please set it to the path of the JNI header files")
endif()

###########################
# Add 3rd party libraries #
###########################
include(cmake/Ultralight.cmake)

find_package(JNI REQUIRED)
if(NOT JNI_FOUND)
    message(SEND_ERROR "JNI could not be found")
endif()

#####################
# Add CMake targets #
#####################
set(UJR_SOURCES
        src/util/JniClass.cpp
        src/util/JniEnv.cpp
        src/util/JniException.cpp
        src/util/JniExceptionCheck.cpp
        src/util/JniMethod.cpp

        src/Platform.cpp
        src/PlatformProvider.cpp
        src/UJRLibrary.cpp)

add_library(ultralight-java-reborn SHARED ${UJR_SOURCES})
target_include_directories(ultralight-java-reborn PRIVATE ${UJR_JNI_HEADER_DIR} ${CMAKE_CURRENT_LIST_DIR}/include)
target_link_libraries(ultralight-java-reborn PRIVATE ultralight JNI::JNI)
target_compile_options(ultralight-java-reborn PRIVATE -fconcepts-diagnostics-depth=10)

############################
# Add install instructions #
############################
install(TARGETS ultralight-java-reborn
        RUNTIME
        DESTINATION bin)
