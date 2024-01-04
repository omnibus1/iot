import React from "react";
import { View, Text, SafeAreaView, StyleSheet} from "react-native";
import SignInScreen from "./src/screens/SignInScreen";
import SignUpScreen from "./src/screens/SignUpScreen";
import Navigation from "./src/navigation";

const App = () => {
  return(
    <SafeAreaView style={styles.root}>
      <Navigation/>
    </SafeAreaView>
  )
}

const styles = StyleSheet.create({
  root:{
    backgroundColor:'white',
    flex:1
  }
})

export default App