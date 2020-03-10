#include <Wire.h>
#include <Adafruit_SSD1306.h>
#include <Adafruit_GFX.h>
#define OLED_ADDR 0X3C

Adafruit_SSD1306 display();
String data;

void setup() {
  int sayac=0;
Serial.begin(9600);
display.begin(SSD1306_SWITCHCAPVCC,OLED_ADDR);
display.clearDisplay();
display.setTextSize(1);
display.setTextColor(WHITE);
display.setCursor(0,0);
display.print("erdogan123");
display.display();
}

void loop() {
if(Serial.available()>0){
  data=Serial.readString();
  Serial.print(data);
  Serial.print("\n");
  delay(500);
  display.begin(SSD1306_SWITCHCAPVCC, OLED_ADDR);
  display.clearDisplay();
  display.setTextSize(1);
  display.setTextColor(WHITE);
  display.setCursor(0,0);
  delay(1000);
  display.print(sayac);
  sayac++;
  display.display();
  
  
  
  }
}
