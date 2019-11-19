const fs = require('fs')

class WordCountObj{
    constructor(word){
        this.word = word;
        this.count = 1;
    }
}

class ParseManager{
    constructor(commandArgs){
        this.commandArgs = commandArgs;
    }

    process(){
        let args = this.commandArgs.length;
        let filePath;
        let fullDoc;
        let searchString;
        let docProcesser;
        let results;
        switch (args) {
            case 4://0 1 wc FP
                filePath = this.commandArgs[3];
                fullDoc = fs.readFileSync(filePath, 'utf8');
                docProcesser = new DoccumentProcessorBuilder().convertCase(new ConvertCase()).findWords(new FindWords())
                    .filterNonAlpha(new StripNonAlphas).countWords(new CountWords).build();
                results = docProcesser.process(fullDoc);
                break;
            case 5://0 1 grep word FP   //.filterLines(FilterLines())
                filePath = this.commandArgs[4];
                fullDoc = fs.readFileSync(filePath, 'utf8');
                searchString = this.commandArgs[3];
                docProcesser = docProcesser = new DoccumentProcessorBuilder().filterLines(new FilterLines(searchString)).build();
                results = docProcesser.process(fullDoc);
                break;
            case 7://0 1 grep word FP "|" wc
                filePath = this.commandArgs[4];
                searchString = this.commandArgs[3];
                fullDoc = fs.readFileSync(filePath, 'utf8');
                docProcesser = new DoccumentProcessorBuilder().filterLines(new FilterLines(searchString)).convertCase(new ConvertCase()).findWords(new FindWords())
                    .filterNonAlpha(new StripNonAlphas).countWords(new CountWords).build();
                results = docProcesser.process(fullDoc);
                break;
            default:
                results = "Wrong number of arguments\n";
                break;
        }
        console.log(results);
    }
}

class DoccumentProcessor{
    // filterLines = null;
    // convertCase;
    // findWords;
    // filterNonAlpha;
    // countWords;
    constructor(filterLines, convertCase, findWords, filterNonAlpha, countWords){
        this.filterLines = filterLines;
        this.convertCase = convertCase;
        this.findWords = findWords;
        this.filterNonAlpha =  filterNonAlpha;
        this.countWords = countWords;
    }
    process(full_doc){
        let results = full_doc;
        if(this.filterLines != null){
            results = this.filterLines.process(results);
        }
        if(this.convertCase != null){
            results = this.convertCase.process(results);
        }
        if (this.findWords != null){
            results = this.findWords.process(results);
        }
        if (this.filterNonAlpha != null){
            results = this.filterNonAlpha.process(results);
        }
        if (this.countWords != null){
            results = this.countWords.process(results);
        }
        return results;
    }
}


class DoccumentProcessorBuilder {
    constructor(){
        this._filterLines = null;
        this._convertCase = null;
        this._findWords = null;
        this._filterNonAlpha = null;
        this._countWords = null;
    }

    filterLines(value) {
        this._filterLines = value;
        return this;
    }

    convertCase(value) {
        this._convertCase = value;
        return this;
    }

    findWords(value) {
        this._findWords = value;
        return this;
    }

    filterNonAlpha(value) {
        this._filterNonAlpha = value;
        return this;
    }

    countWords(value) {
        this._countWords = value;
        return this;
    }

    build(){
        return new DoccumentProcessor(this._filterLines, this._convertCase, this._findWords,
            this._filterNonAlpha, this._countWords)
    }

}
//1. Filter-Lines: filter out lines that don’t meet grep search criteria    -  String -> String
class FilterLines{
    constructor(serachString){
        this.serachString = serachString;
    }
    process(everything){
        let lines = everything.split("\n");
        let docWithGoodLines = "";
        for(let line in lines){
            if(lines[line].indexOf(this.serachString) > -1){
                docWithGoodLines += lines[line] + "\n";
            }
        }
        return docWithGoodLines;
    }
}


//2. Convert-Case: put everything in lowercase                              -  String -> String
class ConvertCase{
    process(everything){
        return everything.toLowerCase();
    }
}

//3. Find-Words: split the text into individual words-  String -> list of all words
class FindWords {
    process(words){
        let allWords = words.split(/\s/);
        return allWords;
    }
}

//4. Non-Alphabetic-Filter: strip out all non-alphabetic characters         -  String -> String
class StripNonAlphas {
    process(allWords){
        let rtn = [];
        for(let word in allWords){
            let current = allWords[word];
            let goodWord = current.replace(/\W/g, '').trim();
            rtn.push(goodWord);
        }
        return rtn;
    }
}


//5. Count-Words: produce a set of unique words and the number of times they each appear    - list of all words -> set of unique words and their counts
class CountWords{
    process(arrayOfAllWords){
        let rtn = new Object();
        //let numOfTimesWordAppears = new [];
        for(let index in arrayOfAllWords) {
            let word = arrayOfAllWords[index];
            if(word.length <= 0){
                continue;
            }
            if (rtn[word] == null) {
                rtn[word] = 1;
            } else {
                rtn[word]++;
            }
        }
        return rtn;
    }
}


let commands = process.argv;
let parser = new ParseManager(commands);
parser.process();

