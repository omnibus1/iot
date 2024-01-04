import React from "react";
import { View, Text, StyleSheet, TextInput} from "react-native";

const CustomInput = ({value, setValue, placeholder, operationFailed}) => {
    return(
        <View style={operationFailed? styles.operationFailed:styles.login}>
            <TextInput
            placeholder={placeholder}
            style={styles.input}
            value={value}
            onChangeText={setValue}
            secureTextEntry={placeholder==="Password"}
            />
        </View>
    )
  };


const styles = StyleSheet.create({
    login:{
        backgroundColor:'white',
        width:'100%',

        borderColor:'gray',
        borderWidth:1,
        borderRadius:5,
        
        paddingHorizontal: 10,
        marginVertical: 5,
        paddingVertical:5,
    },

    operationFailed:{
        backgroundColor:'white',
        width:'100%',

        borderColor:'red',
        borderWidth:1,
        borderRadius:5,
        
        paddingHorizontal: 10,
        marginVertical: 5,
        paddingVertical:5,
    },

    input:{

    }
})
export default CustomInput