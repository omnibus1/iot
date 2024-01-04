import React from "react";
import { useState } from "react";
import { View, Text, StyleSheet} from "react-native";
import CustomInput from "../../components/CustomInput";
import CustomButton from "../../components/CustomButton";
import AsyncStorage from '@react-native-async-storage/async-storage'
import { useNavigation } from "@react-navigation/native";

const SignUpScreen = () => {

    const [username,setUsername] = useState("")
    const [password,setPassword] = useState("")
    const [registerFailed, setRegisterFailed] = useState(false)
    const [responseErrorMessage,setResponseErrorMessage] = useState("")
    const navigation = useNavigation();

    const onRegisterPressed = async () =>{

        const response = await registerUser()
        if(response.ok){
            return navigation.navigate("SignIn");
        }
        const resp_text = await response.json()
        console.log(resp_text);
        setResponseErrorMessage(resp_text.explanation)
        setRegisterFailed(true);
    }
    const registerUser = async () => {
        let registerUrl = process.env.EXPO_PUBLIC_SERVER_ADDRESS+"users/register";
        let response = await fetch(registerUrl,{
            method:"POST",
            headers:{
                Accept: 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                "username":username,
                "password":password
            })
        })
        return response
    }

    return(
        <View style={styles.root}>
            <Text style={styles.title}>Create an account</Text>
            <CustomInput placeholder="Username" value={username} setValue={setUsername} operationFailed={registerFailed}/>
            <CustomInput placeholder="Password" value={password} setValue={setPassword} operationFailed={registerFailed}/>
            <CustomButton text="Register" onPress={onRegisterPressed}/>
            {
                registerFailed && <Text style={styles.textError}>{responseErrorMessage}</Text>
            }

        </View>
    )
  };


const styles = StyleSheet.create({
    root:{
        alignItems:'center',
        padding:20,

        paddingTop:'50%',
        height:"100%"
    },
    title:{
        fontSize:24,
        fontWeight: "bold",
        color:"green",
        margin:10,
    },
    textError:{
        paddingTop:'10%',
        color: "red"
    }
})
export default SignUpScreen