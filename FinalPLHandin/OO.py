import re
import sys

class ParseManager:
    def __init__(self, comand_args):
        self.comand_args = comand_args
        self.create()

    def create(self):
        num_of_args = len(self.comand_args)
        #wc FP
        if (num_of_args == 3):
            self.doc_processor = DoccumentProcessorBuilder().set_fconvertCase(ConvertCase()).set_findWords(FindWords())\
                .set_filterNonAlpha(FilterNonAlpha()).set_countWords(CountWords()).build()
            self.fp = self.comand_args[2]
        #grep word FP
        elif (num_of_args == 4):
            self.doc_processor = DoccumentProcessorBuilder().set_filterLines(FilterLines(self.comand_args[2])).build()
            self.fp = self.comand_args[3]
        #grep word FP "|" wc
        elif (num_of_args == 6):
            self.doc_processor = DoccumentProcessorBuilder().set_filterLines(FilterLines(self.comand_args[2]))\
                .set_fconvertCase(ConvertCase()).set_findWords(FindWords())\
                .set_filterNonAlpha(FilterNonAlpha()).set_countWords(CountWords()).build()
            self.fp = self.comand_args[3]
    def process(self):
        file = open(self.fp)
        doc = ""
        for line in file:
            doc += line
        return self.doc_processor.process(doc)

class DoccumentProcessor:
    def __init__(self, filterLines, convertCase, findWords, filterNonAlpha, countWords):
        self.filterLines = filterLines
        self.convertCase = convertCase
        self.findWords = findWords
        self.filterNonAlpha = filterNonAlpha
        self.countWords = countWords

    def process(self, full_doc):
        results = full_doc
        if(self.filterLines != None):
            results = self.filterLines.process(results)
        if(self.convertCase != None):
            results = self.convertCase.process(results)
        if (self.findWords != None):
            results = self.findWords.process(results)
        if (self.filterNonAlpha != None):
            results = self.filterNonAlpha.process(results)
        if (self.countWords != None):
            results = self.countWords.process(results)
        return results

class DoccumentProcessorBuilder:
    def __init__(self):
        self.filterLines = None
        self.convertCase = None
        self.findWords = None
        self.filterNonAlpha = None
        self.countWords = None

    def set_filterLines(self, filterLines):
        self.filterLines = filterLines
        return self
    def set_fconvertCase(self, convertCase):
        self.convertCase = convertCase
        return self
    def set_findWords(self, findWords):
        self.findWords = findWords
        return self
    def set_filterNonAlpha(self, filterNonAlpha):
        self.filterNonAlpha = filterNonAlpha
        return self
    def set_countWords(self, countWords):
        self.countWords = countWords
        return self
    def build(self):
        return DoccumentProcessor(self.filterLines, self.convertCase,
                                  self.findWords, self.filterNonAlpha, self.countWords)

class FilterLines:
    def __init__(self, search_string):
        self.search_string = search_string

    def process(self, fullDoc):
        rtn = ""
        #fullDoc.replace("[\n\x0B\f\r\x85\u2028\u2029]", "\n")
        #doc_list = fullDoc.split("\n")
        doc_list = re.split("[\n\f\r]", fullDoc)
        for line in doc_list:
            if self.search_string in line:
                rtn += line + " "
        return rtn

class ConvertCase:
    def __init__(self):
        pass

    def process(self, bad_case_doc):
        return bad_case_doc.lower()

class FindWords:
    def __init__(self):
        pass
    def process(self, full_doc):
        rtn = []
        result = full_doc.split()
        for word in result:
            if (len(word) > 0):
                rtn.append(word)
        return rtn
#TODO deal with '
class FilterNonAlpha:
    def __init__(self):
        pass
    def process(self, non_alpha_doc):
        return list(map(lambda word: re.sub('[^a-zA-Z0-9\n ]', "", word), non_alpha_doc))


class CountWords:
    def __init__(self):
        pass
    def process(self, list_of_words):
        rtn = {}
        for word in list_of_words:
            if word in rtn:
                rtn[word] += 1
            else:
                rtn[word] = 1
        return rtn

parser = ParseManager(sys.argv)
print (parser.process())


