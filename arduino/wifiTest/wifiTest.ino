#include <WiFi.h>
#include "WifiClientSecure.h"
#include "Adafruit_MQTT.h"
#include "Adafruit_MQTT_Client.h"
#include "secrets.h"
#include <PubSubClient.h>

WiFiClient wifiClient;

void setup() {
  // put your setup code here, to run once:
  pinMode(LED_BUILTIN, OUTPUT);
  Serial.begin(9600);
  blink(1);

  connectWiFi();

  
}

void loop() {
  // put your main code here, to run repeatedly:
  blink(1);
}

void blink(int count){
  for(int i=0; i < count; i++){
    digitalWrite(LED_BUILTIN, LOW);
    delay(100);
    digitalWrite(LED_BUILTIN, HIGH);
    delay(200);
    digitalWrite(LED_BUILTIN, LOW);
    delay(100);
  }
}

void connectWiFi(){
  Serial.println("Establishing connection to:");
  Serial.println(WLAN_SSID);
  Serial.println(WLAN_PASS);

  if(WiFi.status() == WL_CONNECTED){
    return;
  }

  uint8_t retriesRemaining = 3;
  while(retriesRemaining > 0){
    WiFi.begin(WLAN_SSID, WLAN_PASS);
    if(WiFi.status() == WL_CONNECTED){
      Serial.println("CONNECTED!");
      break;
    }else{
      Serial.println("ATTEMPT FAILED, RETRYING...");
      retriesRemaining--;
      delay(5000);
      blink(2);
    }
  }

  if (retriesRemaining == 0)
  {
    errorRecovery();
  }
}


void errorRecovery()
{
  Serial.println("CONNECTION FAILED!");
  // Delay for 10 blinks then reset device.
  blink(10);
  watchdog_enable(1, 1);
  while(true);
}


void msgReceived(char* topic, byte* payload, unsigned int length) {
  Serial.print("Message received on "); 
  Serial.print(topic); 
  Serial.print(": ");
  for (int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
  }
  Serial.println();
}