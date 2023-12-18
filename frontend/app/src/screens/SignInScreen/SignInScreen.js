import React from "react";
import { useState } from "react";
import { View, Text, StyleSheet, Image} from "react-native";
import Logo from "../../../assets/favicon.png";
import CustomInput from "../../components/CustomInput";
import CustomButton from "../../components/CustomButton";
import { useNavigation } from "@react-navigation/native";

const SignInScreen = () => {

    const [username,setUsername] = useState("")
    const [password,setPassword] = useState("")
    const [failedLogin,setFailedLogin] = useState(false)
    const [logins,setLogins] = useState(["admin"])
    const navigation = useNavigation();

    const onSignInPressed = () =>{
        if (credentialsCorrect()){
            setFailedLogin(false)
            return navigation.navigate("Home")
        }
        setFailedLogin(true)
    }
    const credentialsCorrect = () =>{
        if(username === "admin"){
            return true
        }
        return false
    }

    const onCreateAccountPressed = () =>{
        navigation.navigate("SignUpScreen")
    }

    return(
        <View style={styles.root}>
            <View style={styles.logo}>
                <Image source={Logo}  resizeMode="contain"/>
                <Text>IOT Application</Text>
            </View>
            <CustomInput placeholder="Username" value={username} setValue={setUsername} loginFailed={failedLogin}/>
            <CustomInput placeholder="Password" value={password} setValue={setPassword} loginFailed={failedLogin}/>
            <CustomButton text="Sign in" onPress={onSignInPressed} type="signin"/>
            <CustomButton text="Create account" onPress={onCreateAccountPressed}/>
            <Text style={{display: failedLogin? "flex":"none", color:"red", margin:10}}>The username or password is incorrect</Text>
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
    logo:{
        maxWidth:300,
        maxHeight:300,
        padding:'10%',
        alignItems:'center',
        marginBottom:10
    },
})
export default SignInScreen