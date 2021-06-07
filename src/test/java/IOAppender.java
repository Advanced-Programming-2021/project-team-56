public class IOAppender {
    public static void scoreBoardMenuViewIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("menu show-current\n" +
                "menu enter Duel\n" +
                "invalid command\n" +
                "scoreboard show\n" +
                "menu exit\n");
        outputStringBuilder.append("Scoreboard\r\n" +
                "menu navigation is not possible\r\n" +
                "invalid command\r\n" +
                "1- AmirNick: 0\n" +
                "1- MehrNick: 0\n" +
                "1- abbas: 0\n" +
                "1- aliPalang: 0\n" +
                "1- bothRepetitive: 0\n" +
                "1- cool: 0\n" +
                "1- login: 0\n" +
                "1- mmd: 0\n" +
                "1- mmdi: 0\n" +
                "1- repetitiveNickname: 0\n" +
                "1- rezaiii: 0\n" +
                "1- rza: 0\n" +
                "1- testNickname: 0\n");
    }

    public static void duelViewInvalidRoundsIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("duel --new --second-player username --rounds 1\n" +
                "duel --new --second-player AmirAli --rounds 2\n" +
                "menu exit\n");
        outputStringBuilder.append("username's deck is invalid\r\n" +
                "number of rounds is not supported\r\n");
    }

    public static void shopViewIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("menu show-current\n" +
                "menu enter Duel\n" +
                "invalid command\n" +
                "card show aliabad\n" +
                "card show Hero of the east\n" +
                "increase --money 0\n" +
                "shop show --all\n" +
                "shop buy not a card\n" +
                "shop buy Dark Blade\n" +
                "menu exit\n");
        outputStringBuilder.append("Shop Menu\r\nmenu navigation is not possible\r\ninvalid command\r\n" +
                "card with this name, could not be found!\r\nName: Hero of the east\n" +
                "Level: 3\nType: Normal\nATK: 1100\n" +
                "DEF: 1000\nDescription: Feel da strength ah dis sword-swinging samurai from da Far East.\r\n" +
                "your money increased by the value of 0!\r\n" +
                "Advanced Ritual Art:3000\nAlexandrite Dragon:2600\nAxe Raider:3100\nBaby dragon:1600\n" +
                "Battle OX:2900\nBattle warrior:1300\nBeast King Barbaros:9200\nBitron:1000\nBlack Pendant:4300\n" +
                "Blue-Eyes white dragon:11300\nCall of The Haunted:3500\nChange of Heart:2500\nClosed Forest:4300\n" +
                "Command Knight:2100\nCrab Turtle:10200\nCrawling dragon:3900\nCurtain of the dark ones:700\n" +
                "Dark Blade:3500\nDark Hole:2500\nDark magician:8300\nExploder Dragon:1000\nFeral Imp:2800\n" +
                "Fireyarou:2500\nFlame manipulator:1500\nForest:4300\nGate Guardian:20000\nHaniwa:600\n" +
                "Harpie's Feather Duster:2500\nHerald of Creation:2700\nHero of the east:1700\nHorn Imp:2500\n" +
                "Leotron:2500\nMagic Cylinder:2000\nMagic Jamamer:3000\nMagnum Shield:4300\nMan-Eater Bug:600\n" +
                "Marshmallon:700\nMessenger of peace:4000\nMind Crush:2000\nMirage Dragon:2500\nMirror Force:2000\n" +
                "Monster Reborn:2500\nMystical space typhoon:3500\nNegate Attack:3000\nPot of Greed:2500\n" +
                "Raigeki:2500\nRing of defense:3500\nScanner:8000\nSilver Fang:1700\nSkull Guardian:7900\n" +
                "Slot Machine:7500\nSolemn Warning:3000\nSpell Absorption:4000\nSpiral Serpent:11700\n" +
                "Suijin:8700\nSupply Squad:4000\nSword of dark destruction:4300\nSwords of Revealing Light:2500\n" +
                "Terraforming:2500\nTerratiger, the Empowered Warrior:3200\nTexchanger:200\nThe Calculator:8000\n" +
                "The Tricky:4300\nTime Seal:2000\nTorrential Tribute:2000\nTrap Hole:2000\nTwin Twisters:3500\n" +
                "Umiiruka:4300\nUnited We Stand:4300\nVanity's Emptiness:3500\nWall of Revealing Light:3500\n" +
                "Warrior Dai Grepher:3400\nWattaildragon:5800\nWattkid:1300\nYami:4300\nYomi Ship:1700\n" +
                "there is no card with this name\r\nnot enough money\r\n");
    }

    public static void loginViewIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("menu show-current\n" +
                "user create --username loginViewTest --nickname login --password 12345\n" +
                "menu enter Duel\n" +
                "invalid command\n" +
                "user login --username loginViewTest --password 12345\n" +
                "menu exit\n" +
                "user login --password 12345 --username loginViewTest\n" +
                "menu exit\n" +
                "user login invalid command\n" +
                "menu exit\n");
        outputStringBuilder.append("Login Menu\r\n" +
                "user created successfully!\r\n" +
                "please login first\r\n" +
                "invalid command\r\n" +
                "user logged in successfully!\r\n" +
                "user logged in successfully!\r\n" +
                "invalid command\r\n");
    }

    public static void mainMenuViewIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("invalid command\n" +
                "menu show-current\n" +
                "menu enter Duel\n" +
                "menu exit\n" +
                "menu enter Deck\n" +
                "menu exit\n" +
                "menu enter Shop\n" +
                "menu exit\n" + "" +
                "menu enter Scoreboard\n" +
                "menu exit\n" +
                "menu enter Profile\n" +
                "menu exit\n" +
                "menu enter Import/Export\n" +
                "menu enter invalidMenu\n" +
                "user logout\n" +
                "menu exit\n");
        outputStringBuilder.append("invalid command\r\n" +
                "Main Menu\r\n" +
                "invalid command\r\n" +
                "user logged out successfully!\r\n");
    }

    public static void duelViewNoDeckIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        //TODO
        inputStringBuilder.append("menu show-current\n" +
                "menu enter Duel\n" +
                "menu enter Deck\n" +
                "menu enter Shop\n" +
                "menu enter Scoreboard\n" +
                "menu enter Profile\n" +
                "menu enter Import/Export\n" +
                "duel invalid\n" +
                "invalid\n" +
                "duel --new --second-player noSuchPlayer --rounds 1\n" +
                "duel --new --second-player username --rounds 1\n" +
                "menu exit\n");
        outputStringBuilder.append("Duel Menu\r\n" +
                "menu navigation is not possible\r\n" +
                "menu navigation is not possible\r\n" +
                "menu navigation is not possible\r\n" +
                "menu navigation is not possible\r\n" +
                "menu navigation is not possible\r\n" +
                "menu navigation is not possible\r\n" +
                "invalid command\r\n" +
                "invalid command\r\n" +
                "there is no player with this username\r\n" +
                "repetitiveUsername has no active deck\r\n");
    }

    public static void duelViewInvalidDeckIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("duel --new --second-player repetitiveUsername --rounds 1\n" +
                "duel --new --second-player bothRepetitive --rounds 1\n" +
                "menu exit\n");
        outputStringBuilder.append("repetitiveUsername has no active deck\r\n" +
                "username's deck is invalid\r\n");
    }

    public static void deckMenuViewIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("menu show-current\n"+
                "menu enter Duel\ninvalid\ndeck invalid\ncard show  aa\n" +
                "deck create invalid deckName\ndeck create mehrDeck\ndeck create testDeck\ndeck delete i n\n" +
                "deck delete notExist\ndeck set-activate i n\ndeck set-activate notExist\n" +
                "deck set-activate testDeck\ndeck show --all\n" +
                "deck add-card --card Battle OX --deck testDeck --side\n" +
                "deck add-card --card Battle OX --side --deck testDeck\n" +
                "deck add-card --side --card Battle OX --deck testDeck\n" +
                "deck add-card --deck testDeck --card Command Knight --side\n" +
                "deck add-card --deck testDeck --side --card Axe Raider\n" +
                "deck add-card --side --deck testDeck --card Axe Raider\n" +
                "deck add-card --card Battle OX --side --deck testDeck\n" +
                "deck add-card --card Battle OX --side --deck test\n" +
                "deck add-card -card Battle OX --side --deck testDeck\n" +
                "deck add-card --card Yomi Ship --deck testDeck\n" +
                "deck add-card --deck testDeck --card Yomi Ship\n" +
                "deck add-card --deck testDeck --card Scanner\ndeck add-card --deck testDeck --card Scanner\n" +
                "deck add-card --deck testDeck --card Scanner\ndeck add-card --deck testDeck --card Scanner\n" +
                "deck add-card --deck testDeck -card Yomi Ship\n" +
                "deck rm-card --card Battle OX --deck testDeck --side\n" +//
                "deck rm-card --card Battle OX --side --deck testDeck\n" +
                "deck rm-card --side --card Command Knight --deck testDeck\n" +
                "deck rm-card --deck testDeck --card Yomi Ship --side\n" +
                "deck rm-card --deck test --side --card Battle OX\n" +
                "deck rm-card --side --deck testDeck --card aaaaa\n" +
                "deck rm-card --side --deck testDeck -card aaaaa\n" +
                "deck rm-card --card Yomi Ship --deck testDeck\n" +
                "deck rm-card --deck testDeck --card Battle OX\n" +//
                "deck rm-card --deck test --card Battle OX\n" +
                "deck rm-card --deck testDeck -card Battle OX\ndeck rm-card --deck testDeck --card Scanner\n" +
                "deck rm-card --deck testDeck --card Scanner\ndeck rm-card --deck testDeck --card Scanner\n" +
                "deck show --deck-name testDeck --side\n" +
                "deck show --side --deck-name test\n" +
                "deck show --deck-name testDeck\n" +
                "deck show --ddeck-name testDeck\n" +
                "deck delete testDeck\n" +
                "deck set-activate mehrDeck\n" +
                "menu exit\n");
        outputStringBuilder.append("Deck Menu\r\nmenu navigation is not possible\r\ninvalid command\r\n" +
                "invalid command\r\ninvalid command\r\ninvalid command\r\ndeck with name mehrDeck already exists\r\n" +
                "deck created successfully!\r\ninvalid command\r\ndeck with name notExist does not exist\r\n" +
                "invalid command\r\ndeck with name notExist does not exist\r\ndeck activated successfully\r\n" +
                "Decks:\nActive deck:\ntestDeck: main deck 0, side deck 0, invalid\n" +
                "Other decks:\nmehrDeck: main deck 60, side deck 15, valid\n" +
                "card added successfully\r\n" +
                "card added successfully\r\n" + "card added successfully\r\n" + "card added successfully\r\n" +
                "card added successfully\r\n" + "card added successfully\r\n" + "card with name Battle OX does not exists\r\n" +
                "deck with name test does not exist\r\n" + "invalid command\r\n" + "card added successfully\r\n" +
                "card added successfully\r\n" + "card added successfully\r\n" +
                "card added successfully\r\ncard added successfully\r\n" +
                "there are already three cards with name Scanner in deck testDeck\r\ninvalid command\r\n" +//
                "card removed from deck successfully\r\n" +
                "card removed from deck successfully\r\n" + "card removed from deck successfully\r\n" +
                "card with name Yomi Ship does not exist in side deck\r\n" + "deck with name test does not exist\r\n" +
                "card with name aaaaa does not exist in side deck\r\n" + "invalid command\r\n" + "card removed from deck successfully\r\n" +
                "card with name Battle OX does not exist in main deck\r\n" + "deck with name test does not exist\r\n" +
                "invalid command\r\ncard removed from deck successfully\r\ncard removed from deck successfully\r\n" +
                "card removed from deck successfully\r\n" +
                "Deck: testDeck\nSide deck:\nMonsters:\n" +
                "Axe Raider: An axe-wielding monster of tremendous strength and agility.\n" +
                "Axe Raider: An axe-wielding monster of tremendous strength and agility.\n" +
                "Battle OX: A monster with tremendous power, it destroys enemies with a swing of its axe.\nSpells and Traps:\n" +
                "deck with name test does not exist\n" + "Deck: testDeck\nSide deck:\nMonsters:\n" +
                "Yomi Ship: If this card is destroyed by battle and sent to the GY: Destroy the monster that destroyed this card.\n" +
                "Spells and Traps:\n" + "invalid command\n" +
                "deck deleted successfully\r\n"+
                "deck activated successfully\r\n");
    }

    public static void duelViewAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        //TODO
        //traversing inside the menu
        inputStringBuilder.append("duel invalid\nduel --new --second-player AmirAli --rounds 1\n" +
                "rock\nscissors\nMehrshad\nsurrender\n" +
                "menu exit");
        outputStringBuilder.append("invalid command\r\nMehrNick, please choose between Rock, Paper or Scissors:\r\n" +
                "AmirNick, please choose between Rock, Paper or Scissors:\r\n" +
                "MehrNick, please choose the first player to go: Mehrshad or AmirAli\r\nphase: draw phase\r\n" +
                "new card added to the hand : Mirage Dragon\r\n" + //TODO
                "AmirNick:8000\n\tc\tc\tc\tc\tc\n55\n\tE\tE\tE\tE\tE\n\tE\tE\tE\tE\tE\n0\t\t\t\t\t\tE\n" +
                "\n--------------------------\n\nE\t\t\t\t\t\t0\n\tE\tE\tE\tE\tE\n\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t54\n\tc\tc\tc\tc\tc\tc\nMehrNick:8000\nAmirAli won the whole match\r\n");
    }

    public static void profileViewIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("menu show-current\n" + "menu enter Duel\n" +
                "menu enter Deck\n" + "menu enter Shop\n" +
                "menu enter Scoreboard\n" + "menu enter Profile\n" +
                "menu enter Import/Export\n" +
                "profile change --nickname M k e\n" +
                "profile change --nickname AmirNick\n" +
                "profile change --nickname MehrNick2\n" +
                "profile change --nickname MehrNick\n" +
                "profile change --password --current m a --new m a\n" +
                "profile change --password --current 0000 --new 0000\n" +
                "profile change --password --current 1000 --new 0000\n" +
                "profile change --password --current 0000 --new 1111\n" +
                "profile change --current 1111 --password --new 0000\n" +
                "profile change --current 0000 --new 0000 --password\n" +
                "profile change --password --new 0000 --current 0000\n" +
                "profile change --new 0000 --password --current 0000\n" +
                "profile change --new 0000 --current 0000 --password\ninvalid command\nmenu exit\n");
        outputStringBuilder.append("Profile Menu\r\n" +
                "menu navigation is not possible\r\n" +
                "menu navigation is not possible\r\n" +
                "menu navigation is not possible\r\n" +
                "menu navigation is not possible\r\n" +
                "menu navigation is not possible\r\n" +
                "menu navigation is not possible\r\n" +
                "invalid command\r\n" +
                "user with nickname AmirNick already exists\r\n" +
                "nickname changed successfully\r\n" +
                "nickname changed successfully\r\n" +
                "invalid command\r\n" +
                "please enter a new password\r\n" +
                "current password is invalid\r\n" +
                "password changed successfully\r\n" +
                "password changed successfully\r\n" +
                "please enter a new password\r\n" +
                "please enter a new password\r\n" +
                "please enter a new password\r\n" +
                "please enter a new password\r\n" +
                "invalid command\r\n");
    }
}
