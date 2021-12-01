(ns aoc.day1-test
  (:require [clojure.test :refer :all]
            [aoc.day1 :as sut]))

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
    (let [result (sut/solve sut/test-input)]
     (is (= test-output result)))))
