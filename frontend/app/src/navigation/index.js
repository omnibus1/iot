import React from "react";

import { NavigationContainer } from "@react-navigation/native";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import SignInScreen from "../screens/SignInScreen";
import SignUpScreen from "../screens/SignUpScreen";
import HomeScreen from "../screens/HomeScreen";
import DeviceScreen from "../screens/DeviceScreen";
import AddDeviceScreen from "../screens/AddDeviceScreen";

const Stack = createNativeStackNavigator();

const Navigation = () => {
    return (
        <NavigationContainer>
            <Stack.Navigator screenOptions={{headerShown: false}}>
                <Stack.Screen name="SignIn" component={SignInScreen}/>
                <Stack.Screen name="SignUpScreen" component={SignUpScreen}/>
                <Stack.Screen name="Home" component={HomeScreen}/>
                <Stack.Screen name="Device" component={DeviceScreen}/>     
                <Stack.Screen name="AddDevice" component={AddDeviceScreen}/>  
            </Stack.Navigator>
        </NavigationContainer>
    )
}

export default Navigation