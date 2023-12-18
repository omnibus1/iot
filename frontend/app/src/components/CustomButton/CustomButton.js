import React from "react";
import { View, Text, StyleSheet, Pressable} from "react-native";




const CustomButton=({onPress, text, type})=>{

    return(
        <Pressable onPress={onPress} style={type==="signin"? styles.sing_in:styles.create_account}>
            <Text style={styles.text}>{text}</Text>
        </Pressable>
    )
}

const styles = StyleSheet.create({
    sing_in:{
        backgroundColor: 'blue',
        width: '100%',
        padding:10,
        marginTop: 25,

        alignItems:'center',
        borderRadius:5,
    },
    create_account:{
        backgroundColor: 'green',
        width: '100%',
        padding:10,
        marginTop: 25,

        alignItems:'center',
        borderRadius:5,
    },
    text:{
        fontWeight:'bold',
        color:'white',
    }
})

export default CustomButton