void setup() {
  // put your setup code here, to run once:
  pinMode(LED_BUILTIN, OUTPUT);
  Serial.begin(9600);
  Serial1.begin(9600);
  Serial.println("START");
  while(!Serial1)
    ;
}

char incomingByte = 0x0;
char pm25 = 0x0;
char pm10 = 0x0;
int checksum = 0x0;
int sentChecksum = 0x0;

bool running = true;

void loop() {
  // put your main code here, to run repeatedly:
  
  if(running){
    //stopAutoSend();
    stopFan();
    running=false;
  }else{
    //enableAutoSend();
    startFan();
    running=true;
  }
  for(int i=0; i<15; i++)
  {
    digitalWrite(LED_BUILTIN, HIGH);
    delay(200);
    digitalWrite(LED_BUILTIN, LOW);
    normalReading();
    delay(200);
  }

  
  delay(500);
}

void normalReading()
{
  incomingByte = 0;
  incomingByte = Serial1.read();
  if(incomingByte == 0x42){
    sentChecksum = 0x0;
    checksum = 0x0;
    Serial.println("Received Info!!");
    Serial.print(incomingByte, HEX);
    checksum += incomingByte;
    for(int i=0; i<32; i++)
    {
      Serial.print(" "); 
      incomingByte = Serial1.read();
      if(i<30) checksum += incomingByte;
      else sentChecksum += incomingByte;
      if(i==8){ pm25=incomingByte; }
      if(i==6){ pm10=incomingByte;}
      Serial.print(incomingByte, HEX);
      delay(1);
    }
    Serial.println("");
    Serial.print("PM 2.5: ");
    Serial.print(pm25, HEX);
    Serial.println("");
    Serial.print("PM10: ");
    Serial.print(pm10, HEX);
    Serial.println("");
    Serial.print("Checksum: ");
    Serial.print(checksum, HEX);
    Serial.println("");
    Serial.print("Sent checksum: ");
    Serial.print(sentChecksum, HEX);
    Serial.println("");
  }else{
    Serial.println("Skill Issue");
  }
}


void stopAutoSend()
{
  Serial.println("STOP");
  while (Serial1.available())
    Serial1.read();

  char command[] = {0x68, 0x01, 0x40, 0x77};

  Serial1.write(command, 4);

  char receive[] = {0x0, 0x0};
  //delay(1);
  Serial1.readBytes(receive, 2);
  Serial.print(receive[0], HEX);
  Serial.print(" ");
  Serial.print(receive[1], HEX);
  Serial.println("");
}

void enableAutoSend()
{
  Serial.println("START");
  while (Serial1.available())
    Serial1.read();

  char command[] = {0x68, 0x01, 0x20, 0x57};

  Serial1.write(command, 4);

  char receive[] = {0x0, 0x0};
 
    //delay(1);
    Serial1.readBytes(receive, 2);
    Serial.print(receive[0], HEX);
    Serial.print(" ");
    Serial.print(receive[1], HEX);
    Serial.println("");
}


void stopFan()
{
  Serial.println("STOP FAN");
  while (Serial1.available())
    Serial1.read();

  char command[] = {0x68, 0x01, 0x02, 0x95};

  Serial1.write(command, 4);

  char receive[] = {0x0, 0x0};
  //delay(1);
  Serial1.readBytes(receive, 2);
  Serial.print(receive[0], HEX);
  Serial.print(" ");
  Serial.print(receive[1], HEX);
  Serial.println("");
}

void startFan()
{
  Serial.println("START FAN");
  while (Serial1.available())
    Serial1.read();

  char command[] = {0x68, 0x01, 0x01, 0x96};

  Serial1.write(command, 4);

  char receive[] = {0x0, 0x0};
  //delay(1);
  Serial1.readBytes(receive, 2);
  Serial.print(receive[0], HEX);
  Serial.print(" ");
  Serial.print(receive[1], HEX);
  Serial.println("");
}