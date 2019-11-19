//
// Created by Yehuda Brick on 12/31/18.
//

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct  Node{
    char word[100];
    int numOfTimes;
    struct Node * next;
}Node;

char * filterLines(char * file, char * searchString);           //step 1
char * convertCase(char * doc);                                 //step 2
struct Node * findWords(char * doc);                            //step 3
struct Node * filterNonAlpha(struct Node * docWithBadChars);    //step 4
struct Node * countWords(struct Node * wordList);               //step 5

struct Node * wc(char * file);
struct Node * grepWc(char * file, char * searchString);
char * getFileText(char * filePath);

int main(int argc, char **argv) {
    char *filePath;
    char *fullDoc;
    struct Node *results;
    switch (argc) {
        case 3://wc FP
            filePath = argv[2];
            fullDoc = getFileText(filePath);
            results = wc(fullDoc);
            break;
        case 4://grep word FP
            filePath = argv[3];
            fullDoc = getFileText(filePath);
            fullDoc = filterLines(fullDoc, argv[2]);
            printf("Results: %s\n", fullDoc);
            return 0;
        case 6://grep word FP "|" wc
            filePath = argv[3];
            fullDoc = getFileText(filePath);
            results = grepWc(fullDoc, argv[2]);
            break;
        default:
            results = "Wrong number of arguments\n";
            break;
    }

    printf("Results:\n");
    while (results != NULL) {
        printf("%s: %d\n", results->word, results->numOfTimes);
        results = results->next;
    }
    return 0;
}

char * getFileText(char * filePath){
    size_t size = 0;
    char * rtn = malloc(10);
    char buf[500];
    FILE * file = fopen(filePath, "r");
    while(fgets(&buf, 500, file) != NULL){
        int bufSize = strlen(buf);
        size += bufSize;
        rtn = realloc(rtn, size);
        strcat(rtn, buf);
        memset(buf, '\0', bufSize);
    }
    fclose(file);
    return rtn;
}

struct Node * grepWc(char * file, char * searchString){
    char * doc = filterLines(file, searchString);
    doc = convertCase(doc);
    struct Node * rtn = findWords(doc);
    rtn = filterNonAlpha(rtn);
    return countWords(rtn);
}

struct Node * wc(char * file){
    char * doc = convertCase(file);
    struct Node * rtn = findWords(doc);
    rtn = filterNonAlpha(rtn);
    return countWords(rtn);
}



char * filterLines(char * file, char * searchString){
    char * newFile = (char *)calloc(strlen(file), sizeof(char));
    char * line;
    //TODO see if we need to do \r\n
    line = strtok(file, "\n");

    while(line != NULL){
        if(strstr(line, searchString) != NULL){
            strcat(newFile, "\n");
            strcat(newFile, line);
        }
        line = strtok(NULL, "\n");
    }
    free(file);
    return newFile;
}
//TODO make sure that ther is a null teminator at the end
char * convertCase(char * doc){
    int size = strlen(doc);
    char *newDoc = (char *)calloc(strlen(doc), sizeof(char));

    for(int i = 0; i < size; i++){
        char current = doc[i];
        if(current >= 65 && current <= 90){
            current += 32;
        }
        newDoc[i] = current;
    }
    free(doc);
    return newDoc;
}

//TODO FIX THIS
struct Node * findWords(char * doc){
    struct Node * rtn = (struct Node *)calloc(1, sizeof(struct Node));
    struct Node * current = rtn;
    char * word = calloc(100, sizeof(char));
    char * letter;
    int size = 0;
    int offset = 0;
    char * delim = " \v\t\n\x0B\f\r";

    letter = strpbrk(doc, delim);
    while(letter != NULL){
        memset(word, 0, 100);
        size = letter - doc - offset;
        if(size <= 0){
            offset += 1;
            letter = strpbrk(doc + offset, delim);
            continue;
        }
        strncpy(word, doc + offset, size);
        if(strlen(rtn->word) <= 0){
            strcpy(rtn->word, word);
        }
        else{
            current->next = (struct Node *)calloc(1, sizeof(struct Node));
            current = current->next;
            strcpy(&current->word, word);
        }
        offset += size + 1;

        letter = strpbrk(doc + offset, delim);
    }
    size = strlen(doc + offset);
    if(size <= 0){
        return rtn;
    }
    memset(word, 0, 100);
    strncpy(word, doc + offset, size);
    if(strlen(rtn->word) <= 0){
        strcpy(rtn->word, word);
    }
    else{
        current->next = (struct Node *)calloc(1, sizeof(struct Node));
        current = current->next;
        strcpy(&current->word, word);
    }
    free(doc);
    return rtn;
}

struct Node * filterNonAlpha(struct Node * docWithBadChars){
    //current =  word.replaceAll("\\s|\\W", "");
    struct Node * rtn = (struct Node *)calloc(1, sizeof(struct Node));
    struct Node * currentInNonAlphaList = rtn;
    struct Node * current = docWithBadChars;
    while(current != NULL){
        int size = strlen(current->word);
        int sizeOfWord = 0;
        if(strcmp(&rtn->word, "") == 0){
            for(int i = 0; i < size; i++){
                char curChar = current->word[i];
                if((curChar >= 48 && curChar <= 57) ||
                        (curChar >= 65 && curChar <= 90) ||
                        (curChar >= 97 && curChar <= 122)){
                    rtn->word[sizeOfWord] = curChar;
                    sizeOfWord++;
                }
            }
            rtn->numOfTimes++;
            current = current->next;
        }
        else{
            currentInNonAlphaList->next = (struct Node *)calloc(1, sizeof(struct Node));
            currentInNonAlphaList = currentInNonAlphaList->next;
            for(int i = 0; i < size; i++){
                char curChar = current->word[i];
                if((curChar >= 48 && curChar <= 57) ||
                        (curChar >= 65 && curChar <= 90) ||
                        (curChar >= 97 && curChar <= 122)){
                    currentInNonAlphaList->word[sizeOfWord] = curChar;
                    sizeOfWord++;
                }
            }
            currentInNonAlphaList->numOfTimes++;
            current = current->next;
        }
    }
    return rtn;
}

struct Node * countWords(struct Node * wordList){
    struct Node * rtn = (struct Node *)calloc(1, sizeof(struct Node));
    struct Node * currentInMap = rtn;
    struct Node * current = wordList;

    while(current != NULL){
        int addedToMap = 0;
        while(currentInMap != NULL){
            if(!strcmp(current->word, currentInMap->word)){
                currentInMap->numOfTimes++;
                addedToMap = 1;
                currentInMap = rtn;
                break;
            }
            currentInMap = currentInMap->next;
        }
        if(!addedToMap && !strcmp(&rtn->word, "")){
            strcpy(rtn->word, current->word);
            rtn->numOfTimes++;
            currentInMap = rtn;
        }
        else if(!addedToMap){
            struct Node * temp = (struct Node *)calloc(1, sizeof(struct Node));
            strcpy(temp->word, current->word);
            temp->numOfTimes++;
            temp->next = rtn;
            currentInMap = temp;
            rtn = temp;
        }
        current = current->next;
    }
    return rtn;
}
