package net.janrupf.ujr.api.event;

/**
 * Contains all the integer key codes that can be used in {@link UlKeyEvent#virtualKeyCode}.
 */
@SuppressWarnings("unused")
public final class UlKeyCode {
    private UlKeyCode() {
        throw new RuntimeException("This class may not be instantiated");
    }

    /**
     * GK_BACK (08) BACKSPACE key
     */
    public static final int BACK = 0x08;

    /**
     * GK_TAB (09) TAB key
     */
    public static final int TAB = 0x09;

    /**
     * GK_CLEAR (0C) CLEAR key
     */
    public static final int CLEAR = 0x0C;

    /**
     * GK_RETURN (0D)
     */
    public static final int RETURN = 0x0D;

    /**
     * GK_SHIFT (10) SHIFT key
     */
    public static final int SHIFT = 0x10;

    /**
     * GK_CONTROL (11) CTRL key
     */
    public static final int CONTROL = 0x11;

    /**
     * GK_MENU (12) ALT key
     */
    public static final int MENU = 0x12;

    /**
     * GK_PAUSE (13) PAUSE key
     */
    public static final int PAUSE = 0x13;

    /**
     * GK_CAPITAL (14) CAPS LOCK key
     */
    public static final int CAPITAL = 0x14;

    /**
     * GK_KANA (15) Input Method Editor (IME) Kana mode
     */
    public static final int KANA = 0x15;

    /**
     * GK_HANGUEL (15) IME Hanguel mode (maintained for compatibility; use GK_HANGUL)
     * GK_HANGUL (15) IME Hangul mode
     */
    public static final int HANGUL = 0x15;

    /**
     * GK_IME_ON (16) IME On
     */
    public static final int IME_ON = 0x16;

    /**
     * GK_JUNJA (17) IME Junja mode
     */
    public static final int JUNJA = 0x17;

    /**
     * GK_FINAL (18) IME final mode
     */
    public static final int FINAL = 0x18;

    /**
     * GK_HANJA (19) IME Hanja mode
     */
    public static final int HANJA = 0x19;

    /**
     * GK_KANJI (19) IME Kanji mode
     */
    public static final int KANJI = 0x19;

    /**
     * GK_IME_OFF (1A) IME Off
     */
    public static final int IME_OFF = 0x1A;

    /**
     * GK_ESCAPE (1B) ESC key
     */
    public static final int ESCAPE = 0x1B;

    /**
     * GK_CONVERT (1C) IME convert
     */
    public static final int CONVERT = 0x1C;

    /**
     * GK_NONCONVERT (1D) IME nonconvert
     */
    public static final int NONCONVERT = 0x1D;

    /**
     * GK_ACCEPT (1E) IME accept
     */
    public static final int ACCEPT = 0x1E;

    /**
     * GK_MODECHANGE (1F) IME mode change request
     */
    public static final int MODECHANGE = 0x1F;

    /**
     * GK_SPACE (20) SPACEBAR
     */
    public static final int SPACE = 0x20;

    /**
     * GK_PRIOR (21) PAGE UP key
     */
    public static final int PRIOR = 0x21;

    /**
     * GK_NEXT (22) PAGE DOWN key
     */
    public static final int NEXT = 0x22;

    /**
     * GK_END (23) END key
     */
    public static final int END = 0x23;

    /**
     * GK_HOME (24) HOME key
     */
    public static final int HOME = 0x24;

    /**
     * GK_LEFT (25) LEFT ARROW key
     */
    public static final int LEFT = 0x25;

    /**
     * GK_UP (26) UP ARROW key
     */
    public static final int UP = 0x26;

    /**
     * GK_RIGHT (27) RIGHT ARROW key
     */
    public static final int RIGHT = 0x27;

    /**
     * GK_DOWN (28) DOWN ARROW key
     */
    public static final int DOWN = 0x28;

    /**
     * GK_SELECT (29) SELECT key
     */
    public static final int SELECT = 0x29;

    /**
     * GK_PRINT (2A) PRINT key
     */
    public static final int PRINT = 0x2A;

    /**
     * GK_EXECUTE (2B) EXECUTE key
     */
    public static final int EXECUTE = 0x2B;

    /**
     * GK_SNAPSHOT (2C) PRINT SCREEN key
     */
    public static final int SNAPSHOT = 0x2C;

    /**
     * GK_INSERT (2D) INS key
     */
    public static final int INSERT = 0x2D;

    /**
     * GK_DELETE (2E) DEL key
     */
    public static final int DELETE = 0x2E;

    /**
     * GK_HELP (2F) HELP key
     */
    public static final int HELP = 0x2F;

    /**
     * (30) 0 key
     */
    public static final int NUMBER_0 = 0x30;

    /**
     * (31) 1 key
     */
    public static final int NUMBER_1 = 0x31;

    /**
     * (32) 2 key
     */
    public static final int NUMBER_2 = 0x32;

    /**
     * (33) 3 key
     */
    public static final int NUMBER_3 = 0x33;

    /**
     * (34) 4 key
     */
    public static final int NUMBER_4 = 0x34;

    /**
     * (35) 5 key;
     */
    public static final int NUMBER_5 = 0x35;

    /**
     * (36) 6 key
     */
    public static final int NUMBER_6 = 0x36;

    /**
     * (37) 7 key
     */
    public static final int NUMBER_7 = 0x37;

    /**
     * (38) 8 key
     */
    public static final int NUMBER_8 = 0x38;

    /**
     * (39) 9 key
     */
    public static final int NUMBER_9 = 0x39;

    /**
     * (41) A key
     */
    public static final int A = 0x41;

    /**
     * (42) B key
     */
    public static final int B = 0x42;

    /**
     * (43) C key
     */
    public static final int C = 0x43;

    /**
     * (44) D key
     */
    public static final int D = 0x44;

    /**
     * (45) E key
     */
    public static final int E = 0x45;

    /**
     * (46) F key
     */
    public static final int F = 0x46;

    /**
     * (47) G key
     */
    public static final int G = 0x47;

    /**
     * (48) H key
     */
    public static final int H = 0x48;

    /**
     * (49) I key
     */
    public static final int I = 0x49;

    /**
     * (4A) J key
     */
    public static final int J = 0x4A;

    /**
     * (4B) K key
     */
    public static final int K = 0x4B;

    /**
     * (4C) L key
     */
    public static final int L = 0x4C;

    /**
     * (4D) M key
     */
    public static final int M = 0x4D;

    /**
     * (4E) N key
     */
    public static final int N = 0x4E;

    /**
     * (4F) O key
     */
    public static final int O = 0x4F;

    /**
     * (50) P key
     */
    public static final int P = 0x50;

    /**
     * (51) Q key
     */
    public static final int Q = 0x51;

    /**
     * (52) R key
     */
    public static final int R = 0x52;

    /**
     * (53) S key
     */
    public static final int S = 0x53;

    /**
     * (54) T key
     */
    public static final int T = 0x54;

    /**
     * (55) U key
     */
    public static final int U = 0x55;

    /**
     * (56) V key
     */
    public static final int V = 0x56;

    /**
     * (57) W key
     */
    public static final int W = 0x57;

    /**
     * (58) X key
     */
    public static final int X = 0x58;

    /**
     * (59) Y key
     */
    public static final int Y = 0x59;

    /**
     * (5A) Z key
     */
    public static final int Z = 0x5A;

    /**
     * GK_LWIN (5B) Left Windows key (Microsoft Natural keyboard)
     */
    public static final int LWIN = 0x5B;

    /**
     * GK_RWIN (5C) Right Windows key (Natural keyboard)
     */
    public static final int RWIN = 0x5C;

    /**
     * GK_APPS (5D) Applications key (Natural keyboard)
     */
    public static final int APPS = 0x5D;

    /**
     * GK_SLEEP (5F) Computer Sleep key
     */
    public static final int SLEEP = 0x5F;

    /**
     * GK_NUMPAD0 (60) Numeric keypad 0 key
     */
    public static final int NUMPAD0 = 0x60;

    /**
     * GK_NUMPAD1 (61) Numeric keypad 1 key
     */
    public static final int NUMPAD1 = 0x61;

    /**
     * GK_NUMPAD2 (62) Numeric keypad 2 key
     */
    public static final int NUMPAD2 = 0x62;

    /**
     * GK_NUMPAD3 (63) Numeric keypad 3 key
     */
    public static final int NUMPAD3 = 0x63;

    /**
     * GK_NUMPAD4 (64) Numeric keypad 4 key
     */
    public static final int NUMPAD4 = 0x64;

    /**
     * GK_NUMPAD5 (65) Numeric keypad 5 key
     */
    public static final int NUMPAD5 = 0x65;

    /**
     * GK_NUMPAD6 (66) Numeric keypad 6 key
     */
    public static final int NUMPAD6 = 0x66;

    /**
     * GK_NUMPAD7 (67) Numeric keypad 7 key
     */
    public static final int NUMPAD7 = 0x67;

    /**
     * GK_NUMPAD8 (68) Numeric keypad 8 key
     */
    public static final int NUMPAD8 = 0x68;

    /**
     * GK_NUMPAD9 (69) Numeric keypad 9 key
     */
    public static final int NUMPAD9 = 0x69;

    /**
     * GK_MULTIPLY (6A) Multiply key
     */
    public static final int MULTIPLY = 0x6A;

    /**
     * GK_ADD (6B) Add key
     */
    public static final int ADD = 0x6B;

    /**
     * GK_SEPARATOR (6C) Separator key
     */
    public static final int SEPARATOR = 0x6C;

    /**
     * GK_SUBTRACT (6D) Subtract key
     */
    public static final int SUBTRACT = 0x6D;

    /**
     * GK_DECIMAL (6E) Decimal key
     */
    public static final int DECIMAL = 0x6E;

    /**
     * GK_DIVIDE (6F) Divide key
     */
    public static final int DIVIDE = 0x6F;

    /**
     * GK_F1 (70) F1 key
     */
    public static final int F1 = 0x70;

    /**
     * GK_F2 (71) F2 key
     */
    public static final int F2 = 0x71;

    /**
     * GK_F3 (72) F3 key
     */
    public static final int F3 = 0x72;

    /**
     * GK_F4 (73) F4 key
     */
    public static final int F4 = 0x73;

    /**
     * GK_F5 (74) F5 key
     */
    public static final int F5 = 0x74;

    /**
     * GK_F6 (75) F6 key
     */
    public static final int F6 = 0x75;

    /**
     * GK_F7 (76) F7 key
     */
    public static final int F7 = 0x76;

    /**
     * GK_F8 (77) F8 key
     */
    public static final int F8 = 0x77;

    /**
     * GK_F9 (78) F9 key
     */
    public static final int F9 = 0x78;

    /**
     * GK_F10 (79) F10 key
     */
    public static final int F10 = 0x79;

    /**
     * GK_F11 (7A) F11 key
     */
    public static final int F11 = 0x7A;

    /**
     * GK_F12 (7B) F12 key
     */
    public static final int F12 = 0x7B;

    /**
     * GK_F13 (7C) F13 key
     */
    public static final int F13 = 0x7C;

    /**
     * GK_F14 (7D) F14 key
     */
    public static final int F14 = 0x7D;

    /**
     * GK_F15 (7E) F15 key
     */
    public static final int F15 = 0x7E;

    /**
     * GK_F16 (7F) F16 key
     */
    public static final int F16 = 0x7F;

    /**
     * GK_F17 (80H) F17 key
     */
    public static final int F17 = 0x80;

    /**
     * GK_F18 (81H) F18 key
     */
    public static final int F18 = 0x81;

    /**
     * GK_F19 (82H) F19 key
     */
    public static final int F19 = 0x82;

    /**
     * GK_F20 (83H) F20 key
     */
    public static final int F20 = 0x83;

    /**
     * GK_F21 (84H) F21 key
     */
    public static final int F21 = 0x84;

    /**
     * GK_F22 (85H) F22 key
     */
    public static final int F22 = 0x85;

    /**
     * GK_F23 (86H) F23 key
     */
    public static final int F23 = 0x86;

    /**
     * GK_F24 (87H) F24 key
     */
    public static final int F24 = 0x87;

    /**
     * GK_NUMLOCK (90) NUM LOCK key
     */
    public static final int NUMLOCK = 0x90;

    /**
     * GK_SCROLL (91) SCROLL LOCK key
     */
    public static final int SCROLL = 0x91;

    /**
     * GK_LSHIFT (A0) Left SHIFT key
     */
    public static final int LSHIFT = 0xA0;

    /**
     * GK_RSHIFT (A1) Right SHIFT key
     */
    public static final int RSHIFT = 0xA1;

    /**
     * GK_LCONTROL (A2) Left CONTROL key
     */
    public static final int LCONTROL = 0xA2;

    /**
     * GK_RCONTROL (A3) Right CONTROL key
     */
    public static final int RCONTROL = 0xA3;

    /**
     * GK_LMENU (A4) Left MENU key
     */
    public static final int LMENU = 0xA4;

    /**
     * GK_RMENU (A5) Right MENU key
     */
    public static final int RMENU = 0xA5;

    /**
     * GK_BROWSER_BACK (A6) Windows 2000/XP: Browser Back key
     */
    public static final int BROWSER_BACK = 0xA6;

    /**
     * GK_BROWSER_FORWARD (A7) Windows 2000/XP: Browser Forward key
     */
    public static final int BROWSER_FORWARD = 0xA7;

    /**
     * GK_BROWSER_REFRESH (A8) Windows 2000/XP: Browser Refresh key
     */
    public static final int BROWSER_REFRESH = 0xA8;

    /**
     * GK_BROWSER_STOP (A9) Windows 2000/XP: Browser Stop key
     */
    public static final int BROWSER_STOP = 0xA9;

    /**
     * GK_BROWSER_SEARCH (AA) Windows 2000/XP: Browser Search key
     */
    public static final int BROWSER_SEARCH = 0xAA;

    /**
     * GK_BROWSER_FAVORITES (AB) Windows 2000/XP: Browser Favorites key
     */
    public static final int BROWSER_FAVORITES = 0xAB;

    /**
     * GK_BROWSER_HOME (AC) Windows 2000/XP: Browser Start and Home key
     */
    public static final int BROWSER_HOME = 0xAC;

    /**
     * GK_VOLUME_MUTE (AD) Windows 2000/XP: Volume Mute key
     */
    public static final int VOLUME_MUTE = 0xAD;

    /**
     * GK_VOLUME_DOWN (AE) Windows 2000/XP: Volume Down key
     */
    public static final int VOLUME_DOWN = 0xAE;

    /**
     * GK_VOLUME_UP (AF) Windows 2000/XP: Volume Up key
     */
    public static final int VOLUME_UP = 0xAF;

    /**
     * GK_MEDIA_NEXT_TRACK (B0) Windows 2000/XP: Next Track key
     */
    public static final int MEDIA_NEXT_TRACK = 0xB0;

    /**
     * GK_MEDIA_PREV_TRACK (B1) Windows 2000/XP: Previous Track key
     */
    public static final int MEDIA_PREV_TRACK = 0xB1;

    /**
     * GK_MEDIA_STOP (B2) Windows 2000/XP: Stop Media key
     */
    public static final int MEDIA_STOP = 0xB2;

    /**
     * GK_MEDIA_PLAY_PAUSE (B3) Windows 2000/XP: Play/Pause Media key
     */
    public static final int MEDIA_PLAY_PAUSE = 0xB3;

    /**
     * GK_LAUNCH_MAIL (B4) Windows 2000/XP: Start Mail key
     */
    public static final int MEDIA_LAUNCH_MAIL = 0xB4;

    /**
     * GK_LAUNCH_MEDIA_SELECT (B5) Windows 2000/XP: Select Media key
     */
    public static final int MEDIA_LAUNCH_MEDIA_SELECT = 0xB5;

    /**
     * GK_LAUNCH_APP1 (B6) Windows 2000/XP: Start Application 1 key
     */
    public static final int MEDIA_LAUNCH_APP1 = 0xB6;

    /**
     * GK_LAUNCH_APP2 (B7) Windows 2000/XP: Start Application 2 key
     */
    public static final int MEDIA_LAUNCH_APP2 = 0xB7;

    /**
     * GK_OEM_1 (BA) ';:' for US
     */
    public static final int OEM_1 = 0xBA;

    /**
     * GK_OEM_PLUS (BB) '=+' any country
     */
    public static final int OEM_PLUS = 0xBB;

    /**
     * GK_OEM_COMMA (BC) ',<' any country
     */
    public static final int OEM_COMMA = 0xBC;

    /**
     * GK_OEM_MINUS (BD) '-_' any country
     */
    public static final int OEM_MINUS = 0xBD;

    /**
     * GK_OEM_PERIOD (BE) '.>' any country
     */
    public static final int OEM_PERIOD = 0xBE;

    /**
     * GK_OEM_2 (BF) '/?' for US
     */
    public static final int OEM_2 = 0xBF;

    /**
     * GK_OEM_3 (C0) '`~' for US
     */
    public static final int OEM_3 = 0xC0;

    /**
     * GK_OEM_4 (DB) '[{' for US
     */
    public static final int OEM_4 = 0xDB;

    /**
     * GK_OEM_5 (DC) '\|' for US
     */
    public static final int OEM_5 = 0xDC;

    /**
     * GK_OEM_6 (DD) ']}' for US
     */
    public static final int OEM_6 = 0xDD;

    /**
     * GK_OEM_7 (DE) ''"' for US
     */
    public static final int OEM_7 = 0xDE;

    /**
     * GK_OEM_8 (DF) Used for miscellaneous characters; it can vary by keyboard.
     */
    public static final int OEM_8 = 0xDF;

    /**
     * GK_OEM_102 (E2) Windows 2000/XP: Either the angle bracket key or the backslash key on the RT
     * 102-key keyboard
     */
    public static final int OEM_102 = 0xE2;

    /**
     * GK_PROCESSKEY (E5) Windows 95/98/Me, Windows NT 4.0, Windows 2000/XP: IME PROCESS key
     */
    public static final int PROCESSKEY = 0xE5;

    /**
     * GK_PACKET (E7) Windows 2000/XP: Used to pass Unicode characters as if they were keystrokes. The
     * GK_PACKET key is the low word of a 32-bit Virtual Key value used for non-keyboard input methods.
     * For more information, see Remark in KEYBDINPUT,SendInput, WM_KEYDOWN, and WM_KEYUP
     */
    public static final int PACKET = 0xE7;

    public static final int OEM_ATTN = 0xF0;

    /**
     * GK_ATTN (F6) Attn key
     */
    public static final int ATTN = 0xF6;

    /**
     * GK_CRSEL (F7) CrSel key
     */
    public static final int CRSEL = 0xF7;

    /**
     * GK_EXSEL (F8) ExSel key
     */
    public static final int EXSEL = 0xF8;

    /**
     * GK_EREOF (F9) Erase EOF key
     */
    public static final int EREOF = 0xF9;

    /**
     * GK_PLAY (FA) Play key
     */
    public static final int PLAY = 0xFA;

    /**
     * GK_ZOOM (FB) Zoom key
     */
    public static final int ZOOM = 0xFB;

    /**
     * GK_NONAME (FC) Reserved for future use
     */
    public static final int NONAME = 0xFC;

    /**
     * GK_PA1 (FD) PA1 key
     */
    public static final int PA1 = 0xFD;

    /**
     * GK_OEM_CLEAR (FE) Clear key
     */
    public static final int OEM_CLEAR = 0xFE;

    public static final int UNKNOWN = 0;
}
