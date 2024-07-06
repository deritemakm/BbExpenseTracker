# BB Expense Tracker Project

## Description

The BB Expense Tracker is a Java-coded program with a user-friendly GUI that allows students to enter their expenses by category, date, and amount. These entries are stored in a list sorted by date. The program uses bubble sort to automatically organize the expenses in ascending or descending order. By comparing adjacent elements, bubble sort illustrates the daily spending patterns of students, offering insights into their purchase habits and identifying areas for improvement. The BB Expense Tracker also provides a clear monthly breakdown of expenses, helping students better understand their financial habits.

## Features

- **Login and Sign Up:** Secure authentication system for users to create accounts and log in.
- **Home:** Functionality to add a budget for the week and determine if the user saved money or not.
- **Add Expense:** Allows users to add daily expenses by category, date, and amount.
- **Expenses List Page:** Displays all the entered expenses.
- **Sort Your Expenses Page:** Provides sorting options to organize the expense list.
- **Delete All Expenses:** A button that allows users to delete all the entered expenses at once.
- **JCalendar Integration:** Includes a graphical calendar interface for selecting and displaying dates within the application.

## Prerequisites

Before you begin, ensure you have the following software installed on your machine:

- NetBeans IDE 8.2
- Java Development Kit (JDK) version 1.8.0_111

## Installation and Setup

### Step 1: Download and Install NetBeans IDE 8.2

1. Visit the [official NetBeans download page](https://netbeans.apache.org/download/index.html).
2. Download the installer for NetBeans IDE 8.2.
3. Run the installer and follow the on-screen instructions to complete the installation.

### Step 2: Download and Install JDK 1.8.0_111

1. Visit the [Oracle JDK Archive](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html).
2. Locate and download JDK 1.8.0_111.
3. Run the installer and follow the on-screen instructions to complete the installation.

### Step 3: Configure NetBeans to Use JDK 1.8.0_111

1. Open NetBeans IDE.
2. Go to `Tools > Java Platforms`.
3. Click on `Add Platform`.
4. Browse to the installation directory of JDK 1.8.0_111 and select it.
5. Click `Next` and then `Finish`.

### Step 4: Download and Import JCalendar

1. Download the JCalendar library from [this link](https://toedter.com/jcalendar/) or use the direct link provided in the README file.
2. Extract the downloaded ZIP file to a location on your computer.
3. Video Reference: [this link](https://www.youtube.com/watch?v=robcQaF-jfM)

### Step 5: Import JCalendar into NetBeans

1. In NetBeans IDE, right-click on the `Libraries` folder of your project in the Projects window.
2. Select `Add JAR/Folder...` from the context menu.
3. Navigate to the location where you extracted the JCalendar library files.
4. Select the JAR file (e.g., `jcalendar-1.4.jar`) and click `Open`.
5. Click `OK` to add the JCalendar library to your project.

## Running the Project

1. Clone or download the BB Expense Tracker project from your repository.
2. Open the project in NetBeans IDE.
3. Build and run the project to launch the BB Expense Tracker application.

## Troubleshooting

- **Issue: NetBeans cannot find JDK 1.8.0_111**
  - Ensure that the JDK is properly installed and added to the NetBeans Java Platforms.

- **Issue: Project fails to build**
  - Check the Output window in NetBeans for any error messages.
  - Ensure that all project dependencies are properly set up and that there are no missing libraries.

- **Issue: GUI not displaying correctly**
  - Verify that the NetBeans IDE is up to date.
  - Ensure that your system meets the necessary graphical requirements.

## Additional Resources

- [NetBeans Documentation](https://netbeans.apache.org/kb/index.html)
- [Java SE 8 Documentation](https://docs.oracle.com/javase/8/docs/)

## Author

- Gonzales, Mikaella N.
- Alcaraz Jr., Leo D.
- Landa, James Tristan S.
- Navarro, Wilmer B.

## License

This project is licensed under the MIT License - see the LICENSE.md file for details.

## Acknowledgments

- NetBeans IDE
- Oracle JDK

