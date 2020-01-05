#Made by C.W. - 10/21/2019
#Code anaylzer script
#reads each method in and takes the:
#instance vars used
#class methods used
#other methods used
#lines of code

import csv

iscomment = 0
brackcount = 0
linecount = 0
instancevars = ""
classmethods = ""
othermethods = ""
method = ""
description = ""

DEBUG = 1

startcount = 0
startdescription = 0
regex = ["public", "private", "static", "{"]

loop = "yes"
while (loop == "yes"):
    string = input("Enter local file name: ")
    string = string.strip();
    file = open(string, 'r')
    filewrite = open(string.replace(".java", "") + '.csv', 'w', newline="\n")
    writer = csv.writer(filewrite, delimiter=",", quotechar='"', quoting=csv.QUOTE_MINIMAL)
    writer.writerow(["Method", "Description", "Instance Variables", "Class Methods", "Other Methods", "Line count"])

    for lines in file:
        first = brackcount
        line = lines.strip()
        if "{" in line:
            brackcount += 1
        if "}" in line:
            brackcount -= 1

        #METHOD NAME
        if "{" in line and brackcount == 2:
            startcount = 1
            for r in regex:
                line = line.replace(r, "")
            for c in line:
                if c == "{":
                    break
                else:
                    method += str(c)
            method = method.strip()

        #LINE COUNT
        if startcount:
            if len(line) > 1 and (line[0] + line[1]) == "/*":
                iscomment = 1
            if not iscomment:
                if len(line) > 1 and (line[0] + line[1]) != "//":
                    linecount += 1
            if "*/" in line:
                iscomment = 0

        #DESCRIPTION
        if "/**" in line:
            description = ""
            startdescription = 1

        if "@" in line:
            startdescription = 0
        
        if startdescription:
            description += line.replace("/**", "").replace("*/", "").replace("*", "").strip() + "\n"

        if "*/" in line:
            startdescription = 0

        #INSTANCE VARS
        var = ""
        if brackcount >= 2:
            if "=" in line and "for" not in line and "if" not in line and "while" not in line and "switch" not in line:
                for c in line:
                    if c == " " or c == ".":
                        break
                    else:
                        var += c
                if var not in instancevars and len(var) > 0 and var[0].capitalize() != var[0]:
                    instancevars += var + "\n"
            if "return" in line:
                line = line.replace("return", "");
                line = line.strip()
                for c in line:
                    if c == " " or c == '.':
                        break
                    else:
                        var += c
                if var not in instancevars and len(var) > 0 and var[0].capitalize() != var[0] and var != "new" and var != "null":
                    instancevars += var + "\n"

        #CLASS METHODS
        if brackcount >= 2:
            if "(" in line and ")" in line:
                lastspace = 0
                for c in range(len(line)):
                    newmethod = ""
                    if line[c] == ' ':
                        lastspace = c
                    if line[c] == '(':
                        index = 0
                        if lastspace != 0:
                            index = 1
                        while line[lastspace + index - 1] != ")" and (lastspace + index + 1) < len(line):
                            newmethod += line[lastspace + index]
                            index += 1
                        newmethod = newmethod.strip()
                        if (len(newmethod) > 0):
                            if ("." not in newmethod) and ("new" not in newmethod) and (" (" not in newmethod) and (newmethod[0].capitalize() != newmethod[0]):
                                newnewmethod = ""
                                for c in newmethod:
                                    if c == "(":
                                        break
                                    else:
                                        newnewmethod += str(c)
                                if newnewmethod not in classmethods and newnewmethod not in method:
                                    classmethods += newnewmethod + "\n"



        #OTHER METHODS
        if brackcount >= 2:
            if "." in line and "(" in line and ")" in line:
                lastspace = 0
                for c in range(len(line)):
                    newmethod = ""
                    if line[c] == ' ':
                        lastspace = c
                    if line[c] == '.':
                        index = 0
                        if lastspace != 0:
                            index = 1
                        while line[lastspace + index] != " " and (lastspace + index + 1) < len(line) and not (lastspace + index > c and line[lastspace + index] == "."):
                            if line[lastspace + index] == "(" and lastspace + index > 0:
                                break
                            elif line[lastspace + index] == "(" and lastspace + index == 0:
                                pass
                            else:
                                newmethod += line[lastspace + index]
                            index += 1
                        if newmethod.count(".") == 1:
                            if "System" not in newmethod and "String" not in newmethod and newmethod != "." and newmethod not in othermethods:
                                if "this." in newmethod:
                                    classmethods += newmethod.replace("this.", "") + "\n"
                                else:
                                    othermethods += newmethod + "\n"

        #METHOD DONE
        if "}" in line and brackcount == 1:
            newreg = [";", ")", "("]
            for r in newreg:
                instancevars = instancevars.replace(r,"")
                classmethods = classmethods.replace(r,"")
                othermethods = othermethods.replace(r,"")
            writer.writerow([method, description, instancevars, classmethods, othermethods, linecount - 1])
            method = ""
            description = ""
            othermethods = ""
            classmethods = ""
            instancevars = ""
            startcount = 0
            linecount = 0

    filewrite.close()
    file.close()

    loop = input("Another file? (yes/no): ")
