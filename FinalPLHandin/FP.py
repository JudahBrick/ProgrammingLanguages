import re
import sys
from functools import reduce

def grep_wc(fullDoc, searchString):
    doc_list = re.split("[\n\f\r]", fullDoc)
    fullDoc = list(filter(lambda line: searchString in line, doc_list))
    fullDoc = list(map(lambda line: line.lower(), fullDoc))
    fullDoc = reduce(lambda line, doc: line + " " + doc, fullDoc)
    fullDoc = fullDoc.split()
    fullDoc = list(map(lambda word: re.sub('[^a-zA-Z0-9\n ]', "", word), fullDoc))   #TODO This don't work it broke fix it
    fullDoc = list(filter(lambda word: len(word) > 0, fullDoc))
    rtn = {}
    for word in fullDoc:
        if word in rtn:
            rtn[word] += 1
        else:
            rtn[word] = 1
    return rtn

def grep(fullDoc, searchString):
    doc_list = re.split("[\n\f\r]", fullDoc)
    return list(filter(lambda line: searchString in line, doc_list))

def wc(fullDoc):
    fullDoc.lower()
    fullDoc = fullDoc.split()
    fullDoc = list(map(lambda word: re.sub('[^a-zA-Z0-9\n ]', "", word), fullDoc))  # TODO This don't work it broke fix it
    fullDoc = list(filter(lambda word: len(word) > 0, fullDoc))
    rtn = {}
    for word in fullDoc:
        if word in rtn:
            rtn[word] += 1
        else:
            rtn[word] = 1
    return rtn

def process_file(path):
    file = open(path)
    doc = ""
    for line in file:
        doc += line
    return doc

if __name__ == '__main__':\
length = len(sys.argv)
if (length == 3):
    file_path = sys.argv[2]
    full_doc = process_file(file_path)
    print(wc(full_doc))
# grep word FP
elif (length == 4):
    file_path = sys.argv[3]
    full_doc = process_file(file_path)
    print(grep(full_doc, sys.argv[2]))
# grep word FP "|" wc
elif (length == 6):
    file_path = sys.argv[3]
    full_doc = process_file(file_path)
    print(grep_wc(full_doc, sys.argv[2]))