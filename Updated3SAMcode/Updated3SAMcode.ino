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
const long interval = 300; 
unsigned long blinkTimer = 0;
int blinkState = 0;
int blinkBool = 0;

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
  digitalWrite(ledFrontRight, HIGH);
  digitalWrite(ledFrontLeft, HIGH);
  
  digitalWrite(ledBackRight, HIGH);
  digitalWrite(ledBackLeft, HIGH);
 }
 
 
void loop() {
  //Checks to see if there is incoming data
  if (bluetoothInput.available() > 0) {
    bluetoothInput.readBytes(state, 1);
    //Checks to see where data is coming from and calls the corresponding code
    if (state[0] == 180){
      bluetoothInput.readBytes(state, 2);
      fromPhone(state[0], state[1]);
    }
    else{
       fromComputer(state[0]);
             
       }
  }
  blinkFunc();
  noMoreIncoming();  

    
}

void blinkFunc(){
  unsigned long currentTime = millis();
  if(blinkState == 0){
    //do nothing
    digitalWrite(ledFrontRight, LOW);
    digitalWrite(ledFrontLeft, LOW);
    digitalWrite(ledBackRight, LOW);
    digitalWrite(ledBackLeft, LOW);
  }
  else if(blinkState == 1){
      if(currentTime - blinkTimer > interval){
        blinkTimer = currentTime;
        if(blinkBool){
          digitalWrite(ledFrontRight, HIGH);
          digitalWrite(ledFrontLeft, HIGH);
          digitalWrite(ledBackRight, LOW);
          digitalWrite(ledBackLeft, LOW);
          blinkBool = 0;
        }else{
          digitalWrite(ledFrontRight, LOW);
          digitalWrite(ledFrontLeft, LOW);
          digitalWrite(ledBackRight, LOW);
          digitalWrite(ledBackLeft, LOW);
          blinkBool = 1;
        }
      }
  }

    else if(blinkState == 2){
      if(currentTime - blinkTimer > interval){
        blinkTimer = currentTime;
        if(blinkBool){
          digitalWrite(ledBackRight, HIGH);
          digitalWrite(ledBackLeft, HIGH);
          digitalWrite(ledFrontRight, LOW);
          digitalWrite(ledFrontLeft, LOW);
          blinkBool = 0;
        }else{
          digitalWrite(ledBackRight, LOW);
          digitalWrite(ledBackLeft, LOW);
          digitalWrite(ledFrontRight, LOW);
          digitalWrite(ledFrontLeft, LOW);
          blinkBool = 1;
        }
      }
  }
}
  
void fromComputer (int incomingByte) {
  if(incomingByte == 'w'){
    // if 'f', then go forward
      rightServo.write(0);
      leftServo.write(180);
      blinkState = 1;
    }

  else if(incomingByte == 's') {
    // if 'b', then go backward
      rightServo.write(180);
      leftServo.write(0);
      blinkState = 2;
  }
  
  else if(incomingByte == 'a') {
    // if 'l', then go left
      rightServo.write(0);
      leftServo.write(0);
}
  else if(incomingByte == 'd') {
    // if 'r', then go right
      rightServo.write(180);
      leftServo.write(180);
  }
  else if(incomingByte == 'x') {
    // if 'x', then stop
      rightServo.write(90);
      leftServo.write(90);
   }
   
   ////SLOOOOOOOOW
   else if(incomingByte == 'i'){
    // if 'i', then go forward
      rightServo.write(45);
      leftServo.write(135);
    }

  else if(incomingByte == 'k') {
    // if 'k', then go backward
      rightServo.write(135);
      leftServo.write(45);
  }
  
  else if(incomingByte == 'j') {
    // if 'j', then go left
      rightServo.write(80);
      leftServo.write(80);
}
  else if(incomingByte == 'l') {
    // if 'l', then go right
      rightServo.write(100);
      leftServo.write(100);
    }

    else if(incomingByte == ' ') {
    // if '/space/', then stop
      rightServo.write(90);
      leftServo.write(90);
      blinkState = 0;
  }
  
}

void fromPhone (int leftMotor, int rightMotor) {
  //Sets the speed of the motor to the position of the sliders in the app
  leftServo.write(leftMotor);
  rightServo.write(180- rightMotor);
  
  //All Leds On
  digitalWrite(ledEyeRight, HIGH);
  digitalWrite(ledEyeLeft, HIGH);
  digitalWrite(ledFrontRight, HIGH);
  digitalWrite(ledFrontLeft, HIGH);
  digitalWrite(ledBackRight, HIGH);
  digitalWrite(ledBackLeft, HIGH);
  
  //These are the variables for the noMoreIncoming function
   ptime = millis();
   reset = 0;
   return;
}


void noMoreIncoming(){
  currentTime = millis(); 
  if (reset == 0 && currentTime - ptime >= 150){
    leftServo.write(90);
    rightServo.write(90);
    reset = 1;
  }
}

