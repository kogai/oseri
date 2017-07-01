(ns oseri.model-test
  (:use
   oseri.model
   oseri.view)
  (:require [clojure.test :refer :all]
            [oseri.core :refer :all]))

(deftest test-successor
  (testing "should derive successor"
    (is (= (successor :N {:row 1 :col 1}) {:row 0 :col 1}))
    (is (= (successor :NE {:row 1 :col 1}) {:row 0 :col 2}))
    (is (= (successor :E {:row 1 :col 1}) {:row 1 :col 2}))
    (is (= (successor :SE {:row 1 :col 1}) {:row 2 :col 2}))
    (is (= (successor :S {:row 1 :col 1}) {:row 2 :col 1}))
    (is (= (successor :SW {:row 1 :col 1}) {:row 2 :col 0}))
    (is (= (successor :W {:row 1 :col 1}) {:row 1 :col 0}))
    (is (= (successor :NW {:row 1 :col 1}) {:row 0 :col 0}))))

(deftest test-correct-line
  (testing "should correct line"
    (let [board '(({:color :empty :row 0 :col 0} {:color :black :row 0 :col 1} {:color :white :row 0 :col 2})
                  ({:color :empty :row 1 :col 0} {:color :black :row 1 :col 1} {:color :white :row 1 :col 2})
                  ({:color :empty :row 2 :col 0} {:color :black :row 2 :col 1} {:color :white :row 2 :col 2}))]
      (is (= (correct-line :N {:row 2 :col 1} board) '({:color :black :row 2 :col 1} {:color :black :row 1 :col 1} {:color :black :row 0 :col 1}))))))
