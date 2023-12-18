import React from "react";
import { View, Text, StyleSheet } from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";

const HomeScreen = () => {
    return (
        <SafeAreaView style={styles.root}>
            <Text>
                Home
                wad
            </Text>
        </SafeAreaView>
    )
}
const styles = StyleSheet.create({
    root:{
        alignItems:'center',
        padding:20,
        backgroundColor:'lightgray',
        padding:10,
        height:"100%"
    }
})

export default HomeScreen