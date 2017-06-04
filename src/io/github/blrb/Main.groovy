package io.github.blrb

def pathToOutFile = "by.txt"
def pathToFolderNkorpusGrammar = "lib/nkorpus-grammar"

def dir = new File(pathToFolderNkorpusGrammar)
println("Path to folder with grammar database: ${dir.absolutePath}")

println("Start script...")

def addWordsFromFile = {
    pathToFile ->
        println("Parsing grammar database: ${pathToFile.absolutePath}")
        def wordsInFile = []
        def xmlWordList = new XmlParser().parse(pathToFile)
        xmlWordList.Paradigm.each { wordsInFile << it.@Lemma.replace("*", "") }
        println("Find ${wordsInFile.size()} words in file")
        wordsInFile
}

def uniqueElementInSortArray = {
    array ->
        def uniqueWords = []
        array.eachWithIndex{ word, idx ->
            if (array[idx] != array[idx + 1]) uniqueWords << word
        }
        uniqueWords
}

def words = []
dir.listFiles().sort().findAll { it.path.endsWith(".xml") }.each { words.addAll(addWordsFromFile(it)) }
println()
words = words.sort()
words = uniqueElementInSortArray(words)
println("Find words: ${words.size()}")

def outFile = new File(pathToOutFile)
outFile.withWriter{ out ->
    words.each { out.println it}
}

println("Save file: ${outFile.absolutePath}")
