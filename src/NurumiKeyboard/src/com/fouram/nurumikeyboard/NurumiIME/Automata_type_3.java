package com.fouram.nurumikeyboard.NurumiIME;

import android.util.Log;
import android.view.inputmethod.InputConnection;

public class Automata_type_3 {

  public static final boolean ENABLE_DEBUG = true;
  // 0.ㄱ 1.ㄲ 2.ㄴ 3.ㄷ 4.ㄸ 5.ㄹ 6.ㅁ 7.ㅂ 8.ㅃ 9.ㅅ 10.ㅆ 11.ㅇ 12.ㅈ 13.ㅉ 14.ㅊ 15.ㅋ 16.ㅌ 17.ㅍ 18ㅎ
  private static final int[] PREF_CHO = {12593, 12594, 12596, 12599, 12600, 12601, 12609, 12610,
      12611, 12613, 12614, 12615, 12616, 12617, 12618, 12619, 12620, 12621, 12622};
  // 0.ㅏ 1.ㅐ 2.ㅑ 3.ㅒ 4.ㅓ 5.ㅔ 6.ㅕ 7.ㅖ 8.ㅗ 9.ㅘ 10.ㅙ 11.ㅚ 12.ㅛ 13.ㅜ 14.ㅝ 15.ㅞ 16.ㅟ 17.ㅠ 18.ㅡ 19.ㅢ 20.ㅣ
  private static final int[] PREF_JUNG = {12623, 12624, 12625, 12626, 12627, 12628, 12629, 12630,
      12631, 12632, 12633, 12634, 12635, 12636, 12637, 12638, 12639, 12640, 12641, 12642, 12643};
  // 0.ㄱ 1.ㄲ 2.ㄳ 3.ㄴ 4.ㄵ 5.ㄶ 6.ㄷ 7.ㄹ 8.ㄺ 9.ㄻ 10.ㄼ 11.ㄽ 12.ㄾ 13.ㄿ 14.ㅀ 15.ㅁ 16.ㅂ 17.ㅄ 
  // 18.ㅅ 19.ㅆ 20.ㅇ 21.ㅈ 22.ㅊ 23.ㅋ 24.ㅌ 25.ㅍ 26.ㅎ
  private static final int[] PREF_JONG = {12593, 12594, 12595, 12596, 12597, 12598, 12599, 12601,
      12602, 12603, 12604, 12605, 12606, 12607, 12608, 12609, 12610, 12612, 12613, 12614, 12615,
      12616, 12618, 12619, 12620, 12621, 12622};
  private static final int AC00 = 44032;

  public static final int THUMB_FINGER = 0;
  public static final int INDEX_FINGER = 1;
  public static final int MIDLE_FINGER = 2;
  public static final int RING__FINGER = 3;
  public static final int PINKY_FINGER = 4;

  public static final int DIRECTION_EMPTY = -1;
  public static final int DIRECTION_DOT = 0;
  public static final int DIRECTION_DOWN = 1;
  public static final int DIRECTION_LEFT = 2;
  public static final int DIRECTION_UP = 3;
  public static final int DIRECTION_RIGHT = 4;

  public static final int LEVEL_CHO_SEONG = 0;
  public static final int LEVEL_JUNG_SEONG = 1;
  public static final int LEVEL_JUNG_SEONG_TO_JONG_SEONG = 2;
  public static final int LEVEL_JONG_SEONG = 3;
  public static final int LEVEL_JONG_SEONG_TO_CHO_SEONG = 4;

  public static final int CHO_SEONG = 0;
  public static final int JUNG_SEONG = 1;
  public static final int JONG_SEONG = 2;
  public static final int WISP_FLARE = 3;

  public static int buffer[] = {'\0', '\0', '\0', '\0'};
  public static int automata_level;

  public static int count_finger = 0;
  public static String str_to_write = null;
  public static int[] finger;
  public static InputConnection ic;

  // yoon // 150516 // get a Korean character code key value
  public static int generate_korean_char_code(int cho_seong, int jung_seong, int jong_seong) {
    return ((AC00 + ((cho_seong * 21) + jung_seong) * 28) + jong_seong);
  }
  
  // yoon // 150517 // replace character macro
  public static int replace_to(int ret) {
    ic.deleteSurroundingText(1, 0);
    return ret;
  }
  public static void buffer_clean() {
    int i = 3;
    while (i-->0)
      buffer[i] = 0;
  }

  // yoon // 150516 // div&conq.

  public static void LEVEL_CHO_SEONG() {


    // yoon // 150517 // buffer clean
    buffer_clean();
    
    switch (count_finger) { // yoon // step 2. switch by finger counts for accuracy

      case 1: // yoon // 150413 // case for single finger

        // yoon // 150413 // Conditional Statements for 'ja-eum cho-seong'
                // 150517 // mod ipt method
        if (finger[INDEX_FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 11;// 'ㅇ'
          str_to_write = String.format("%c", PREF_CHO[buffer[CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[MIDLE_FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 6; // 'ㅁ'
          str_to_write = String.format("%c", PREF_CHO[buffer[CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 2; // 'ㄴ'
          str_to_write = String.format("%c", PREF_CHO[buffer[CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[PINKY_FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 5; // 'ㄹ'
          str_to_write = String.format("%c", PREF_CHO[buffer[CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        }


        // yoon // 150413 // Conditional Statements for 'mo-eum letter'
        else if (finger[INDEX_FINGER] != DIRECTION_EMPTY)
          switch (finger[INDEX_FINGER]) {

            case DIRECTION_UP:
              str_to_write = "ㅗ";
              break;
            case DIRECTION_RIGHT:
              str_to_write = "ㅏ";
              break;
            case DIRECTION_DOWN:
              str_to_write = "ㅜ";
              break;
            case DIRECTION_LEFT:
              str_to_write = "ㅓ";
              break;
          }
        break; // yoon // 150413 // break for single finger

      case 2: // yoon // 150424 // case for two fingers
                      // 150517 // mod ipt method
        if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 0;// 'ㄱ'
          str_to_write = String.format("%c", PREF_CHO[buffer[CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 3;// 'ㄷ'
          str_to_write = String.format("%c", PREF_CHO[buffer[CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 7;// 'ㅂ'
          str_to_write = String.format("%c", PREF_CHO[buffer[CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        }

        // yoon // 150507 // Conditional Statements for 'mo-eum jung_seong'
        else if (finger[INDEX_FINGER] != DIRECTION_EMPTY && finger[MIDLE_FINGER] != DIRECTION_EMPTY) {
          switch (finger[INDEX_FINGER]) {

            case DIRECTION_UP:
              str_to_write = "ㅛ";
              break;
            case DIRECTION_RIGHT:
              str_to_write = "ㅑ";
              break;
            case DIRECTION_DOWN:
              str_to_write = "ㅠ";
              break;
            case DIRECTION_LEFT:
              str_to_write = "ㅕ";
              break;

          }
        }
        break; // yoon // 150413 // break for two fingers

      case 3: // yoon // 150507 // case for three fingers
                      // 150517 // mod ipt method
        if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
            && finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 12; // 'ㅈ'
          str_to_write = String.format("%c", PREF_CHO[buffer[CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT
            && finger[PINKY_FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 9; // 'ㅅ'
          str_to_write = String.format("%c", PREF_CHO[buffer[CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        }

        break;// yoon // 150507 // break for three fingers

      case 4: // yoon // 150507 // case for four fingers

        if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
            && finger[RING__FINGER] != DIRECTION_EMPTY && finger[PINKY_FINGER] != DIRECTION_EMPTY) {
          buffer[CHO_SEONG] = 18;// 'ㅎ'
          str_to_write = String.format("%c", PREF_CHO[buffer[CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        }

        else {

          switch (finger[INDEX_FINGER]) {
            case DIRECTION_RIGHT:
              str_to_write = "ㅡ";
              break;
            case DIRECTION_DOWN:
              str_to_write = "ㅣ";
              break;
            default:
              str_to_write = "";
              break;
          }
        }
        break;// yoon // 150507 // break for four fingers

      default:
        str_to_write = "";
    }
  };

  public static void LEVEL_JUNG_SEONG() {
    switch (count_finger) { // yoon // step 2. switch by finger counts for accuracy

      case 1: // yoon // 150413 // case for single finger
                      // 150517 // mod ipt method
        if (finger[INDEX_FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 11;// 'ㅇ'
          str_to_write = String.format("%c", PREF_CHO[buffer[CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[MIDLE_FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 6; // 'ㅁ'
          str_to_write = String.format("%c", PREF_CHO[buffer[CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 2; // 'ㄴ'
          str_to_write = String.format("%c", PREF_CHO[buffer[CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[PINKY_FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 5; // 'ㄹ'
          str_to_write = String.format("%c", PREF_CHO[buffer[CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        }

        // yoon // 150413 // Conditional Statements for 'hout-mo-eum jung_seong' 
        
        else if (finger[INDEX_FINGER] != DIRECTION_EMPTY) {
          switch (finger[INDEX_FINGER]) {

            case DIRECTION_UP:
              buffer[JUNG_SEONG] = 8; // 'ㅗ'
              break;
            case DIRECTION_RIGHT:
              buffer[JUNG_SEONG] = 0; // 'ㅏ'
              break;
            case DIRECTION_DOWN:
              buffer[JUNG_SEONG] = 13;// 'ㅜ'
              break;
            case DIRECTION_LEFT:
              buffer[JUNG_SEONG] = 4; // 'ㅓ'
              break;
          }
          ic.deleteSurroundingText(1, 0);
          str_to_write =
              String.format("%c",
                  generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0));
          automata_level = LEVEL_JUNG_SEONG_TO_JONG_SEONG;
        }

        break; // yoon // 150413 // break for single finger

      case 2: // yoon // 150413 // case for two fingers 
                      // 150517 // mod ipt method

        if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = (buffer[CHO_SEONG] == 0 ? replace_to(15) : 0);// 'ㄱ' or 'ㅋ'
          str_to_write = String.format("%c", PREF_CHO[buffer[CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = (buffer[CHO_SEONG] == 3 ? replace_to(16) : 3);// 'ㄷ' or 'ㅌ'
          str_to_write = String.format("%c", PREF_CHO[buffer[CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = (buffer[CHO_SEONG] == 7 ? replace_to(17) : 7);// 'ㅂ' or 'ㅍ'
          str_to_write = String.format("%c", PREF_CHO[buffer[CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        }

        // yoon // 150507 // Conditional Statements for 'hout-mo-eum jung_seong'

        else if (finger[INDEX_FINGER] != DIRECTION_EMPTY && finger[MIDLE_FINGER] != DIRECTION_EMPTY) {
          switch (finger[INDEX_FINGER]) {

            case DIRECTION_UP:
              buffer[JUNG_SEONG] = 12;// 'ㅛ'
              break;
            case DIRECTION_RIGHT:
              buffer[JUNG_SEONG] = 2; // 'ㅑ'
              break;
            case DIRECTION_DOWN:
              buffer[JUNG_SEONG] = 17; // 'ㅠ'
              break;
            case DIRECTION_LEFT:
              buffer[JUNG_SEONG] = 6; // 'ㅕ'
              break;
          }

          ic.deleteSurroundingText(1, 0);
          str_to_write =
              String.format("%c",
                  generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0));
          automata_level = LEVEL_JUNG_SEONG_TO_JONG_SEONG;
        }
        
        // yoon // 150517 // Conditional Statements for 'bok-mo-eum jung_seong'

        else if (finger[INDEX_FINGER] != DIRECTION_EMPTY && finger[RING__FINGER] != DIRECTION_EMPTY) {
          switch (finger[INDEX_FINGER]) {

            case DIRECTION_RIGHT:
              buffer[JUNG_SEONG] = 1; // 'ㅐ'
              break;
            case DIRECTION_LEFT:
              buffer[JUNG_SEONG] = 5; // 'ㅔ'
              break;
          }

          ic.deleteSurroundingText(1, 0);
          str_to_write =
              String.format("%c",
                  generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0));
          automata_level = LEVEL_JUNG_SEONG_TO_JONG_SEONG;
        }

        break; // yoon // 150413 // break for two fingers

      case 3: // yoon // 150507 // case for three fingers
        if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
            && finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = (buffer[CHO_SEONG] == 12 ? replace_to(14) : 12); // 'ㅈ' or 'ㅊ'
          str_to_write = String.format("%c", PREF_CHO[buffer[CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT
            && finger[PINKY_FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 9; // 'ㅅ'
          str_to_write = String.format("%c", PREF_CHO[buffer[CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        }
        
        
        // yoon // 150517 // for bok-mo-eum jung-seong
        else if (finger[INDEX_FINGER] != DIRECTION_EMPTY
            && finger[MIDLE_FINGER] != DIRECTION_EMPTY && finger[RING__FINGER] != DIRECTION_EMPTY) {
          switch (finger[INDEX_FINGER]) {

            case DIRECTION_RIGHT:
              buffer[JUNG_SEONG] = 3; // 'ㅒ'
              break;
            case DIRECTION_LEFT:
              buffer[JUNG_SEONG] = 7; // 'ㅖ'
              break;
          }
          ic.deleteSurroundingText(1, 0);
          str_to_write =
              String.format("%c",
                  generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0));
          automata_level = JONG_SEONG;
        }
        break;

      case 4: // yoon // 150516 // case for four fingers

        if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
            && finger[RING__FINGER] != DIRECTION_EMPTY && finger[PINKY_FINGER] != DIRECTION_EMPTY) {
          buffer[CHO_SEONG] = 18;// 'ㅎ'
          str_to_write = String.format("%c", PREF_CHO[buffer[CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[INDEX_FINGER] != DIRECTION_EMPTY
            && finger[MIDLE_FINGER] != DIRECTION_EMPTY && finger[RING__FINGER] != DIRECTION_EMPTY
            && finger[PINKY_FINGER] != DIRECTION_EMPTY) {
          switch (finger[INDEX_FINGER]) {
            case DIRECTION_RIGHT:
              buffer[JUNG_SEONG] = 18; // 'ㅡ'
              break;
            case DIRECTION_DOWN:
              buffer[JUNG_SEONG] = 20; // 'ㅣ'
              break;
          }
          ic.deleteSurroundingText(1, 0);
          str_to_write =
              String.format("%c",
                  generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0));
          automata_level = LEVEL_JUNG_SEONG_TO_JONG_SEONG;
        } 
    }
  };

  public static void LEVEL_JUNG_SEONG_TO_JONG_SEONG() {
    switch (count_finger) { // yoon // step 2. switch by finger counts for accuracy

      case 1: // yoon // 150517 // case for single finger

        if (buffer[JUNG_SEONG] == 8 && finger[INDEX_FINGER] == DIRECTION_RIGHT) { // 'ㅗ' + 'ㅏ'
          buffer[JUNG_SEONG] = 9; // 'ㅘ'
          ic.deleteSurroundingText(1, 0);
          str_to_write =
              String.format("%c",
                  generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0));
          automata_level = JONG_SEONG;
        } else if (buffer[JUNG_SEONG] == 13 && finger[INDEX_FINGER] == DIRECTION_LEFT) { // 'ㅜ' + 'ㅓ'
          buffer[JUNG_SEONG] = 14; // 'ㅝ'
          ic.deleteSurroundingText(1, 0);
          str_to_write =
              String.format("%c",
                  generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0));
          automata_level = JONG_SEONG;
        } else 
          LEVEL_JONG_SEONG();
        
        break;
        
      case 2: // yoon // 150517 // case for two fingers
        
        if (buffer[JUNG_SEONG] == 8 && finger[INDEX_FINGER] == DIRECTION_RIGHT
            && finger[RING__FINGER] != DIRECTION_EMPTY) { // 'ㅗ' + 'ㅐ'
          buffer[JUNG_SEONG] = 10; // 'ㅚ'
          ic.deleteSurroundingText(1, 0);
          str_to_write =
              String.format("%c",
                  generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0));
          automata_level = JONG_SEONG;
        } else if (buffer[JUNG_SEONG] == 13 && finger[INDEX_FINGER] == DIRECTION_LEFT
            && finger[RING__FINGER] != DIRECTION_EMPTY) { // 'ㅜ' + 'ㅔ'
          buffer[JUNG_SEONG] = 15; // 'ㅞ'
          ic.deleteSurroundingText(1, 0);
          str_to_write =
              String.format("%c",
                  generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0));
          automata_level = JONG_SEONG;
        } else 
          LEVEL_JONG_SEONG();
        
        break;
        
      case 3: // yoon // 150517 // case for three fingers

        LEVEL_JONG_SEONG();
        break;
        
      case 4: // yoon // 150517 // case for four fingers
        if (finger[INDEX_FINGER] == DIRECTION_DOWN && finger[MIDLE_FINGER] == DIRECTION_DOWN
            && finger[RING__FINGER] != DIRECTION_EMPTY && finger[PINKY_FINGER] != DIRECTION_EMPTY) {
          if (buffer[JUNG_SEONG] == 8 ) { //'ㅗ'
            buffer[JUNG_SEONG] = 11; // 'ㅚ'
            ic.deleteSurroundingText(1, 0);
            str_to_write =
                String.format("%c",
                    generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0));
            automata_level = JONG_SEONG;
          } else if (buffer[JUNG_SEONG] == 13 ) { //'ㅜ'
            buffer[JUNG_SEONG] = 16; // 'ㅟ'
            ic.deleteSurroundingText(1, 0);
            str_to_write =
                String.format("%c",
                    generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0));
            automata_level = JONG_SEONG;
          } else if (buffer[JUNG_SEONG] == 0 ) { //'ㅏ'
            buffer[JUNG_SEONG] = 1; // 'ㅐ'
            ic.deleteSurroundingText(1, 0);
            str_to_write =
                String.format("%c",
                    generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0));
            automata_level = JONG_SEONG;
          } else if (buffer[JUNG_SEONG] == 2 ) { //'ㅑ'
            buffer[JUNG_SEONG] = 3; // 'ㅒ'
            ic.deleteSurroundingText(1, 0);
            str_to_write =
                String.format("%c",
                    generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0));
            automata_level = JONG_SEONG;
          } else if (buffer[JUNG_SEONG] == 4 ) { //'ㅓ'
            buffer[JUNG_SEONG] = 5; // 'ㅔ'
            ic.deleteSurroundingText(1, 0);
            str_to_write =
                String.format("%c",
                    generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0));
            automata_level = JONG_SEONG;
          } else if (buffer[JUNG_SEONG] == 6 ) { //'ㅕ'
            buffer[JUNG_SEONG] = 7; // 'ㅖ'
            ic.deleteSurroundingText(1, 0);
            str_to_write =
                String.format("%c",
                    generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0));
            automata_level = JONG_SEONG;
          } else if (buffer[JUNG_SEONG] == 18 ) { //'ㅡ'
            buffer[JUNG_SEONG] = 19; // 'ㅢ'
            ic.deleteSurroundingText(1, 0);
            str_to_write =
                String.format("%c",
                    generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0));
            automata_level = JONG_SEONG;
          }
        }
    }

  };

  public static void LEVEL_JONG_SEONG() {
    switch (count_finger) { // yoon // step 2. switch by finger counts for accuracy

      case 1: // yoon // 150413 // case for single finger

        // yoon // 150517 // Conditional Statements for 'ja-eum jong-seong'
        if (finger[INDEX_FINGER] == DIRECTION_DOT) {
          buffer[JONG_SEONG] = 21;// 'ㅇ'
          buffer[WISP_FLARE] = 11;// 'ㅇ'
          ic.deleteSurroundingText(1, 0);
          str_to_write = String.format("%c",
              generate_korean_char_code(buffer[CHO_SEONG],buffer[JUNG_SEONG],buffer[JONG_SEONG]));
          automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
        } else if (finger[MIDLE_FINGER] == DIRECTION_DOT) {
          buffer[JONG_SEONG] = 16;// 'ㅁ'
          buffer[WISP_FLARE] = 6;// 'ㅁ'
          ic.deleteSurroundingText(1, 0);
          str_to_write = String.format("%c",
              generate_korean_char_code(buffer[CHO_SEONG],buffer[JUNG_SEONG],buffer[JONG_SEONG]));
          automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
        } else if (finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[JONG_SEONG] = 4;// 'ㄴ'
          buffer[WISP_FLARE] = 2;// 'ㄴ'
          ic.deleteSurroundingText(1, 0);
          str_to_write = String.format("%c",
              generate_korean_char_code(buffer[CHO_SEONG],buffer[JUNG_SEONG],buffer[JONG_SEONG]));
          automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
        } else if (finger[PINKY_FINGER] == DIRECTION_DOT) {
          buffer[JONG_SEONG] = 8;// 'ㄹ'
          buffer[WISP_FLARE] = 5;// 'ㄹ'
          ic.deleteSurroundingText(1, 0);
          str_to_write = String.format("%c",
              generate_korean_char_code(buffer[CHO_SEONG],buffer[JUNG_SEONG],buffer[JONG_SEONG]));
          automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
        }
        break;
      case 2: // yoon // 150424 // case for two fingers
       
        // yoon // 150517 // Conditional Statements for 'ja-eum jong-seong'
        if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT) {
          buffer[JONG_SEONG] = 1;// 'ㄱ'
          buffer[WISP_FLARE] = 0;// 'ㄱ'
          ic.deleteSurroundingText(1, 0);
          str_to_write = String.format("%c",
              generate_korean_char_code(buffer[CHO_SEONG],buffer[JUNG_SEONG],buffer[JONG_SEONG]));
          automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
        } else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[JONG_SEONG] = 7;// 'ㄷ'
          buffer[WISP_FLARE] = 3;// 'ㄷ'
          ic.deleteSurroundingText(1, 0);
          str_to_write = String.format("%c",
              generate_korean_char_code(buffer[CHO_SEONG],buffer[JUNG_SEONG],buffer[JONG_SEONG]));
          automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
        } else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[JONG_SEONG] = 17;// 'ㅂ'
          buffer[WISP_FLARE] = 7;// 'ㅂ'
          ic.deleteSurroundingText(1, 0);
          str_to_write = String.format("%c",
              generate_korean_char_code(buffer[CHO_SEONG],buffer[JUNG_SEONG],buffer[JONG_SEONG]));
          automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
        }
    }
  };

  public static void LEVEL_JONG_SEONG_TO_CHO_SEONG() {
    
    // yoon // 150517 // Conditional Statements for Wisp phenomenon 
    if(finger[INDEX_FINGER] != DIRECTION_EMPTY && finger[INDEX_FINGER] != DIRECTION_DOT) {
    
      str_to_write = String.format("%c",
          generate_korean_char_code(buffer[CHO_SEONG],buffer[JUNG_SEONG],0));
      ic.deleteSurroundingText(1, 0);
      ic.commitText(String.valueOf(str_to_write+'x'),2);
      //ic.commitText(String.valueOf('x'),1);
      int wisp_flare = buffer[WISP_FLARE];
      buffer_clean();
      buffer[CHO_SEONG] = wisp_flare;
      LEVEL_JUNG_SEONG();
    }
    else LEVEL_CHO_SEONG();
  };


  // yoon // THIS IS WHAT I'M REALLY WANT TO DO !!
  public static String execute(int[] finger_array, InputConnection input_connection) {


    // yoon // 150516 // init values
    int idx = 5;
    finger = finger_array;
    ic = input_connection;
    count_finger = 0;

    // yoon // 150412 // count finger
    while (idx-- > 0)
      if (finger[idx] != DIRECTION_EMPTY)
        count_finger++;

    if (ENABLE_DEBUG == true) { // yoon // 150413 // for debug

      Log.d("Automata bgn", "automata level : " + automata_level);
      Log.d("Automata bgn", "finger count : " + count_finger);
      Log.d("Automata bgn", "current buffer : " + buffer[0] + " " + buffer[1] + " " + buffer[2]
          + " " + buffer[3]);
      Log.d("Automata bgn", "motion : " + finger[THUMB_FINGER] + " " + finger[INDEX_FINGER] + " "
          + finger[MIDLE_FINGER] + " " + finger[RING__FINGER] + " " + finger[PINKY_FINGER]);
    }

    // yoon // 150507 // functional keys

    if (count_finger == 1 && finger[THUMB_FINGER] == DIRECTION_RIGHT) {
      automata_level = LEVEL_CHO_SEONG;
      return " ";
    }

    else if (count_finger == 1 && finger[PINKY_FINGER] == DIRECTION_LEFT) {
      automata_level = LEVEL_CHO_SEONG;
      ic.deleteSurroundingText(1, 0);
      return "";
    }

    else if (count_finger == 2 && finger[THUMB_FINGER] == DIRECTION_DOT
        && finger[PINKY_FINGER] == DIRECTION_DOT) {
      automata_level = LEVEL_CHO_SEONG;
      return "\n";
    }

    // yoon // 150413 // switch by automata level
    // yoon // 150516 // Code refactoring : Devide & Conquer

    switch (automata_level) { // yoon // step 1. switch by automata level

      case LEVEL_CHO_SEONG:
        LEVEL_CHO_SEONG();
        break;

      case LEVEL_JUNG_SEONG:
        LEVEL_JUNG_SEONG();
        break;

      case LEVEL_JUNG_SEONG_TO_JONG_SEONG:
        LEVEL_JUNG_SEONG_TO_JONG_SEONG();
        break;

      case LEVEL_JONG_SEONG:
        LEVEL_JONG_SEONG();
        break;

      case LEVEL_JONG_SEONG_TO_CHO_SEONG:
        LEVEL_JONG_SEONG_TO_CHO_SEONG();
        break;
    }

    return str_to_write;
  }


}
