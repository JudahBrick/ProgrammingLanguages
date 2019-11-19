echo =========================  STARTING TESTS  FOR YEHUDA BRICK ==============================

echo ""
echo PROGRAM ARGS = $*
echo ""

echo =========================  C TESTS ==============================

echo ""
echo --- Imperative C ---

gcc Imperative.c -w
./a.out $*

echo ""
echo --- Functional C ---

gcc Functional.c -w
./a.out $*

echo =========================  JAVA TESTS ==============================

echo ""
echo --- Object Oriented Java ---
javac -d JavaComped JavaSource/*.java

java -cp "JavaComped/" ParseManager $*

echo ""
echo --- Functional Java ---

javac FunctionalJavaWooHoo.java
java FunctionalJavaWooHoo $*



echo =========================  PYTHON TESTS ==============================
echo ""
echo --- Object Oriented Python ---
python OO.py $*

echo ""
echo --- Functional Python ---
python FP.py $*

echo =========================  JAVASCRIPT TESTS ==============================

echo ""
echo ----  JS Object Oriented ---
node OOJS.js $*

rm ./a.out
rm -r *.class