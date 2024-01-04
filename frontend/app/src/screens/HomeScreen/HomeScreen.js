import React from "react";
import { View, Text, StyleSheet, ScrollView, RefreshControl, Pressable } from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";
import { useState, useEffect } from "react";
import AsyncStorage from "@react-native-async-storage/async-storage";
import DeviceBasic from "../../components/DeviceBasic";
import Icon from 'react-native-vector-icons/FontAwesome';
import { useNavigation } from "@react-navigation/native";

const HomeScreen = () => {
    const [userName, setUsername] = useState("")
    const [readingData, setReadingData] = useState([])
    const navigation = useNavigation();

    useEffect(()=>{
        tryToSetUserName();
        fetchData()
    }, [])

    const tryToSetUserName = async () => {
        try{
            const username = await AsyncStorage.getItem("username");
            setUsername(username)
        }
        catch (error){
            console.log(error)
        }
    }

    const fetchData = async () => {
        let readingsUrl = process.env.EXPO_PUBLIC_SERVER_ADDRESS+"api/devices_info?username=adam123";
        let data = await fetch(readingsUrl)
        .then(res => {
            if(res.ok)return res.json()
            else return []
        })
        .catch(error => console.warn(error))
        setReadingData(data)
        data.forEach((device)=>{
            console.log(device.device_id)
        })
    }

    function devicePressed(){
        navigation.navigate("AddDevice")
    }

    return (
        <SafeAreaView style={styles.root}>
            <Text>
                Welcome {userName}!
            </Text>
            <View style={styles.container}>
                <ScrollView  overScrollMode={'always'} style={styles.scrollContainer}>
                    <View style={styles.deviceList}>
                        {readingData.map((device, idx)=><DeviceBasic deviceData={device} key={idx}></DeviceBasic>)}
                    </View>
                </ScrollView>
            </View>
            {readingData.length === 0 &&<Text>You dont have any devices</Text>}
            <Pressable onPress={()=>devicePressed()} style={styles.button}>
                <Icon name="plus" size={20}></Icon>
            </Pressable>

        </SafeAreaView>
    )
}
const styles = StyleSheet.create({
    root:{
        alignItems:'center',

        padding:5,
        height:"100%",
        display:"flex",
        rowGap:20,

    },
    deviceList:{
        display:"flex",
        rowGap:5,
    },
    button:{
        display:"flex",
        position: 'absolute',
        width:75,
        backgroundColor:"lightgreen",
        height:75,
        bottom:10,
        borderRadius:1000,
        justifyContent:"center",
        alignItems:"center",
        shadowColor: 'black',
        elevation: 3,
    },
    text:{
        fontWeight: '600',
    },
    empty:{
        position:"absolute",
        bottom:0
    },
    container:{
        flex:1,
        borderWidth:0,
    },
    scrollContainer:{
        flex:1,
        borderWidth:0,
    }
})

export default HomeScreen