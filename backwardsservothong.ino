#include <Servo.h>
int sensor = A0;
int sensor2 = A1; 
Servo myservo;
Servo myservo2;
int count = 0;
int count2=0;


void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(sensor, INPUT);
  pinMode(sensor2, INPUT);
  myservo2.attach(11);
  myservo.attach(9);

}
void loop() {
  // put your main code here, to run repeatedly:
  
  int readSensor = analogRead(sensor);
  int readSensor2 = analogRead(sensor2);
  Serial.println(readSensor);


  if (readSensor > 350 && count < 1) {
    myservo.write(80);
    
    delay(1000);
    myservo.write(90);
    count++;
    count2 = 0;

  }

  else {
    if (readSensor <= 350 && count2 < 1) {
      myservo.write(100); 
      delay(1000);
      myservo.write(90);
      count = 0; 
      count2++;
      
      
    }

   else {
      myservo.write(90);
    }
  }
  
}
