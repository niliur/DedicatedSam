#include <SoftwareSerial.h>
#include <Servo.h>

const int rxPin = 13; //SoftwareSerial RX pin, connect to JY-MCY TX pin
const int txPin = 12; //SoftwareSerial TX pin, connect to JY-MCU RX pin
// level shifting to 3.3 volts may be needed

SoftwareSerial bluetoothInput(rxPin, txPin); // RX, TX
Servo leftServo;
Servo rightServo;
int ledEyeRight = 11;  // LED eye (right)
int ledEyeLeft = 10;  // LED eye (left)
int ledFrontRight = 5;  // LED front (right)
int ledFrontLeft = 6;  // LED front (left)
int ledBackRight = 3;  // LED back (right)
int ledBackLeft = 4;  // LED back (left)

unsigned long currentTime = 0;
unsigned long ptime = 0;
int compstate = 0;

unsigned long currentMillis = 0;
unsigned long previousMillis = 0;

//my blinkpair function variables
const long interval = 100; 
int ledState = HIGH; // constant used to control LED blinking

int reset = 0;
byte state[] = {90, 90};

byte phoneIncoming[] = {200, 90, 90};
byte  computerIncoming;

void setup() {
  // sets the pins as outputs:
  bluetoothInput.begin(9600);
  Serial.begin(9600);
  leftServo.attach(9);
  rightServo.attach(8);
  pinMode(ledEyeRight, OUTPUT);
  pinMode(ledEyeLeft, OUTPUT);
  pinMode(ledFrontRight, OUTPUT);
  pinMode(ledFrontLeft, OUTPUT);
  pinMode(ledBackRight, OUTPUT);
  pinMode(ledBackLeft, OUTPUT);
  digitalWrite(ledEyeRight, HIGH);
  digitalWrite(ledEyeLeft, HIGH);
 }
 
 
void loop() {
  // put your main code here, to run repeatedly:
  if (bluetoothInput.available() > 0) {
    bluetoothInput.readBytes(state, 1);
    if (state[0] == 180){
    bluetoothInput.readBytes(state, 2);
      fromPhone(state[0], state[1]);
    }
    else{
       fromComputer(state[0]);
       currentMillis = millis();
       if (currentMillis - previousMillis > interval){
           previousMillis = currentMillis;
           if (compstate == 1){
               compstate = blinkOff(state[0]);
           }
           else if(compstate == 0){
               compstate = blinkOn(state[0]);
            
           }
             
       }
    }
  }
  stopPhone();  

    
}
  
void fromComputer (int incomingByte) {
  if(incomingByte == 'w'){
    // if 'f', then go forward
      rightServo.write(0);
      leftServo.write(180);
      //blinkPair(ledFrontLeft, ledFrontRight);
    }

  else if(incomingByte == 's') {
    // if 'b', then go backward
      rightServo.write(180);
      leftServo.write(0);
      //blinkPair(ledBackLeft, ledBackRight);
  }
  
  else if(incomingByte == 'a') {
    // if 'l', then go left
      rightServo.write(0);
      leftServo.write(0);
     // blinkPair(ledFrontLeft, ledBackLeft);    
}
  else if(incomingByte == 'd') {
    // if 'r', then go right
      rightServo.write(180);
      leftServo.write(180);
     // blinkPair(ledFrontRight, ledBackRight);
  }
  else if(incomingByte == 'x') {
    // if 'x', then stop
      rightServo.write(90);
      leftServo.write(90);
   }
   
   ////SLOOOOOOOOW
   else if(incomingByte == 'i'){
    // if 'f', then go forward
      rightServo.write(45);
      leftServo.write(135);
   //   blinkPair(ledFrontLeft, ledFrontRight);
    }

  else if(incomingByte == 'k') {
    // if 'b', then go backward
 
      rightServo.write(135);
      leftServo.write(45);
    //  blinkPair(ledBackLeft, ledBackRight);
  }
  
  else if(incomingByte == 'j') {
    // if 'l', then go left
      rightServo.write(80);
      leftServo.write(80);
  //    blinkPair(ledFrontLeft, ledBackLeft);    
}
  else if(incomingByte == 'l') {
    // if 'r', then go right
      rightServo.write(100);
      leftServo.write(100);
//      blinkPair(ledFrontRight, ledBackRight);
  }

    else if(incomingByte == ' ') {
    // if 'r', then go right
      rightServo.write(90);
      leftServo.write(90);
  }
  
}

void fromPhone (int leftMotor, int rightMotor) {
  leftServo.write(leftMotor);
  rightServo.write(180- rightMotor);
  if (leftMotor > 125){
    //digitalWrite(ledFrontLeft, HIGH);
    //digitalWrite(ledBackLeft, LOW);
  } 
  else if(leftMotor < 55){
   // digitalWrite(ledFrontLeft, LOW);
   // digitalWrite(ledBackLeft, HIGH);
  }
  else{
   // digitalWrite(ledFrontLeft, LOW);
   // digitalWrite(ledBackLeft, LOW);     
  }
  if (rightMotor > 125){
   // digitalWrite(ledFrontRight, HIGH);
   // digitalWrite(ledBackRight, LOW);
  }
  else if(rightMotor < 55){
   // digitalWrite(ledFrontRight, LOW);
   // digitalWrite(ledBackRight, HIGH);
  }
  else{
   // digitalWrite(ledFrontRight, LOW);
   // digitalWrite(ledBackRight, LOW);
      
  }
   ptime = millis();
   reset = 0;
   return;
}
void stopPhone(){
  currentTime = millis(); 
  if (reset == 0 && currentTime - ptime >= 150){
    leftServo.write(90);
    rightServo.write(90);
    digitalWrite(ledFrontLeft, LOW);
    digitalWrite(ledBackLeft, LOW);
    digitalWrite(ledFrontRight, LOW);
    digitalWrite(ledBackRight, LOW);  
    reset = 1;
  }
}

int blinkOn (int incomingByte) {
  digitalWrite(ledFrontLeft, HIGH);
  digitalWrite(ledBackLeft, HIGH);
  digitalWrite(ledFrontRight, HIGH);
  digitalWrite(ledBackRight, HIGH);

    return 1;
}

int blinkOff (int incomingByte) {
    digitalWrite(ledFrontLeft, LOW);
    digitalWrite(ledBackLeft, LOW);
    digitalWrite(ledFrontRight, LOW);
    digitalWrite(ledBackRight, LOW);

    return 0;
}

