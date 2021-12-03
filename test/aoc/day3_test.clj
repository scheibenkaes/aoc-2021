(ns aoc.day3-test
  (:require [clojure.test :refer :all]
            [aoc.day3 :as sut]))

(def test-input ["00100"
                 "11110"
                 "10110"
                 "10111"
                 "10101"
                 "01111"
                 "00111"
                 "11100"
                 "10000"
                 "11001"
                 "00010"
                 "01010"])


(deftest solve-test
  (testing "test input leads to output"
    (let [{gamma :gamma epsilon :epsilon bits :bits mult :mult} (sut/solve test-input)]
      (is (not (nil? bits)))
      (is (= gamma 22))
      (is (= epsilon 9))
      (is (= mult 198)))))

#_(deftest ^:integration solve-on-input-file
  (let [result (sut/run-on-test-file!)]
    (is (= 1855814 (:mult result)))))

#_(deftest pt2-test
  (let [result (sut/pt2-solve test-input)]
    (is (= (:mult result) 900))))

#_(deftest ^:integration run-pt2-test
  (let [result (sut/run-pt2!)]
    (is (= 1845455714 (:mult result)))))
