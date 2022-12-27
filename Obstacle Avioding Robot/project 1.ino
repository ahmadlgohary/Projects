#include <AFMotor.h> 
#define trigPin 12 
#define echoPin 13 
AF_DCMotor motor1(1, MOTOR12_64KHZ);
AF_DCMotor motor2(2, MOTOR12_64KHZ);
 
void setup() {
  Serial.begin(9600); 
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  motor1.setSpeed(255); 
  motor2.setSpeed (255);  
}
 
void loop() {
  long duration, distance;
  digitalWrite(trigPin, LOW);  
  delayMicroseconds(2); 
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10); 
  digitalWrite(trigPin, LOW);
  duration = pulseIn(echoPin, HIGH);
  distance = (duration/2) / 29.1;
  motor2.run(FORWARD);  
    if (distance < 25){   
      motor1.run(BACKWARD);  
    }
    else {
      delay (15);
      motor1.run(FORWARD); 
    } 
}
