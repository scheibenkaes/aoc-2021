(ns aoc.day1-test
  (:require [clojure.test :refer :all]
            [aoc.day1 :as sut]))

(def test-input [199
                 200
                 208
                 210
                 200
                 207
                 240
                 269
                 260
                 263])

(def test-output
  [[199 nil]
   [200 :increased]
   [208 :increased]
   [210 :increased]
   [200 :decreased]
   [207 :increased]
   [240 :increased]
   [269 :increased]
   [260 :decreased]
   [263 :increased]])


(deftest solve-test
  (testing "test input leads to test output"
    (let [result (sut/solve test-input)]
     (is (= test-output result)))))

(deftest intermediates-to-result-test
  (testing "finds the correct answer for test input data"
    (let [result (sut/solve test-input)
          result (sut/intermediates-to-result result)]
      (is (= 7 result)))))

(deftest ^:integration run-on-test-file!-test
  (let [result (sut/run-on-test-file!)]
    (is (= 1532 result))))


;; ---------------- PT 2

(def windowed
  [[607 nil]
   [618 :increased]
   [618 :no-change]
   [617 :decreased]
   [647 :increased]
   [716 :increased]
   [769 :increased]
   [792 :increased]])

(deftest pt2-test
  (testing "intermediate results are generated as expected"
    (let [result (sut/pt2 test-input)]
      (is (= result windowed))))

  (testing "calculates the example correctly"
    (let [result (-> test-input sut/pt2 sut/intermediates-to-result)]
      (is (= 5 result)))))

(deftest ^:integration test-pt2-on-test-file
  (let [result (sut/run-pt2!)]
    (is (= result 1571))))
