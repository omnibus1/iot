import React from "react";
import { useState } from "react";
import { View, Text, StyleSheet} from "react-native";
import CustomInput from "../../components/CustomInput";
import CustomButton from "../../components/CustomButton";
import AsyncStorage from '@react-native-async-storage/async-storage'

const SignUpScreen = () => {

    const [username,setUsername] = useState("")
    const [password,setPassword] = useState("")

    const onRegisterPressed = () =>{
        console.warn("create account")
    }

    return(
        <View style={styles.root}>
            <Text style={styles.title}>Create an account</Text>
            <CustomInput placeholder="Username" value={username} setValue={setUsername}/>
            <CustomInput placeholder="Password" value={password} setValue={setPassword}/>
            <CustomButton text="Register" onPress={onRegisterPressed}/>
        </View>
    )
  };


const styles = StyleSheet.create({
    root:{
        alignItems:'center',
        padding:20,
        backgroundColor:'lightgray',
        paddingTop:'25%',
        height:"100%"
    },
    title:{
        fontSize:24,
        fontWeight: "bold",
        color:"green",
        margin:10,
    }
})
export default SignUpScreen