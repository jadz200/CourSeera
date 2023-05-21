# CourSeera: University Course Scraper Application

This repository contains a Java application for scraping courses from a university website, converting them to CSV format, and providing sorting capabilities. The application also includes custom CLI commands for interacting with the data and a Telegram bot integration for convenient access.

## Installation

To run the application, follow these steps:

1. Clone the repository:


2. Navigate to the project directory:


3. Set up the required dependencies:

- Make sure you have Java Development Kit (JDK) installed.

4. Build the application:
```
javac Main.java

```
5. Run the application:
```
java Main
```

The application should now be running.

## Usage

The application provides the following functionality:

- Scraping courses from a university website.
- Converting the scraped data to CSV format.
- Sorting the course data based on different criteria.

### Custom CLI Commands

The application supports custom CLI commands to interact with the data. Here are the available commands:


### Telegram Bot Integration

The application includes a Telegram bot integration for easy access to course information. To use the Telegram bot, follow these steps:

1. Create a new Telegram bot and obtain the bot token. Refer to the Telegram Bot API documentation for instructions.

2. Update the bot token in the `config.properties` file located in the project's root directory.

3. Run the application:


4. Start a conversation with the bot on Telegram.

- Search for the bot using the username or invite the bot to a group.
- Type `/start` to initialize the conversation.

5. Use the following commands to interact with the bot:



