###############
# CMake setup #
###############
cmake_minimum_required(VERSION 3.26)
project(ultralight-java-reborn)

#####################
# Set CMake options #
#####################
set(CMAKE_CXX_STANDARD 17)

if(NOT DEFINED UJR_JNI_HEADER_DIR)
    # Usually set via gradle, but CMake can also be invoked manually
    message(SEND_ERROR "Missing UJR_JNI_HEADER_DIR, please set it to the path of the JNI header files")
endif()

###########################
# Add 3rd party libraries #
###########################
include(cmake/Ultralight.cmake)

find_package(JNI REQUIRED)
if (NOT JNI_FOUND)
    message(SEND_ERROR "JNI could not be found")
endif()

#####################
# Add CMake targets #
#####################
set(UJR_SOURCES
        src/platform_provider.cpp)

add_library(ultralight-java-reborn SHARED ${UJR_SOURCES})
target_include_directories(ultralight-java-reborn PRIVATE ${UJR_JNI_HEADER_DIR})
target_link_libraries(ultralight-java-reborn PRIVATE ultralight JNI::JNI)

############################
# Add install instructions #
############################
install(TARGETS ultralight-java-reborn
        RUNTIME
        DESTINATION bin)
