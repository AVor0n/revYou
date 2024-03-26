CREATE TABLE Users (
    ID INT PRIMARY KEY,
    Name VARCHAR(50),
    Surname VARCHAR(50),
    Email VARCHAR(100),
    Password VARCHAR(50),
    Role VARCHAR(10)
);

CREATE TABLE Teachers (
    ID INT PRIMARY KEY,
    Subject VARCHAR(50),
    FOREIGN KEY (ID) REFERENCES Users(ID)
);

CREATE TABLE Students (
    ID INT PRIMARY KEY,
    Admission_Year INT,
    FOREIGN KEY (ID) REFERENCES Users(ID)
);