# RPGCreator-Server

Management & Game Server for RPG Creator Client, see [RPGCreator Github](https://github.com/opensourcegamedev/RPGCreator).

## System Requirements
  - Java Runtime Environment (JRE)
  - MySQL Database 5.0+ (or above)
  - minumum 256MB RAM
  
## Installation

First you need to install an mysql server. After this, create an database with database user.
Create an configuration file **data/config/mysql.cfg** with following content:
```
[MySQL]
host=<IP>
port=3306
user=<DB User>
password=<DB Password>
dbName=<DB Name>
prefix=rpg_
```

Replace values with your settings!
After this step, you have to start the management server.

```bash
java -jar <Management Server File>.jar data/config
```