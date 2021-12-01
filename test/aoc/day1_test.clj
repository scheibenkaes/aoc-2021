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
