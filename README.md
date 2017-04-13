# E-mail pranker
## Project description
This project implements an e-mail pranker. It allows you to send an e-mail from a predefined list of message to a list of victims.

Choose the number of victim's groups you want and the pranker forms the groups from your victim's list. The first member of each group appear as the e-mail's sender and the others are the recipients.

Disclaimer : this application should only be used to prank volunteers. Don't use it to send spam, your SMTP server would be quickly blacklisted.

This project has been realized for the RES course at HEIG-VD, Yverdon-les-Bains.

## Requirement
- Java's JRE >= 1.8
- A SMTP Server (MockMock server provided for testing)

## Installation
### From the sources
This project is build with Maven. Then you just have to type the following command in the project's root to generate a `.jar` executable :

`mvn clean install`

Then move the built jar to the root (or any other folder) :

`mv target/smtp_lab-0.0.1-SNAPSHOT.jar .`

After configuring, you can launch the `.jar` executable :

`java -jar smtp_lab-0.0.1-SNAPSHOT.jar`

### From the binary
You can just execute the `.jar` binary provided at the project's root, after configuration :

`java -jar smtp_lab.jar`

## Configuration
To run the pranker, the folder containing the `.jar` executable must contain a `config/` folder. This folder will contain the following files :

```
config/config.properties
config/messages.utf8
config/victims.utf8```

### config.properties
This file contains the following configuration :
```
numberOfGroups=6
SMTPServerAddress=localhost
SMTPServerPort=2525
```

### messages.utf8
This file contains the messages that will be randomly picked to be sent to the groups. They must respect the following convention :
```
Subject of the message
Content of the message,
can be multilines.
---
Other message's subject
Other message's content
---
```

### victims.utf8
This file contains the victims that will either sent or receive the pranks. Just provide a list of e-mail adresses :
```
john.doe@example.com
jane.doe@other-example.com
```

As a group must contain at least 3 members, you must provide at least 3 * numberOfGroups victims.

# Using the mock server
To test the pranker, you can run MockMock server provided in this project's repository. Just run `java -jar MockMock-1.4.0.jar -p 2525` where `2525` is the listening port.

You can then access `http://localhost:8282/` to show the e-mails the pranker would have sent.

You can also download MockMock's last version from the repository https://github.com/tweakers-dev/MockMock
