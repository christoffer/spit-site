(ns spit-site.helpers-test
  (:use clojure.test
        spit-site.helpers
        [clojure.java.io :only [file]]))

(deftest relativize-test
  (testing "Relativize with sub dir"
    (is (= "a/b/c" (relativize
                     (file "base/dir")
                     (file "base/dir/a/b/c")))))

  (testing "Relativize is empty when path is not a sub path"
    (is (= nil (relativize
                 (file "a/b/c")
                 (file "d/e/f"))))))

(deftest path-of-test
  (testing "splits a path and a filename in two"
    (let [the-file (file "the/path/the-filename.txt")
          the-path (path-of the-file)]
      (is (= "the/path" the-path))))

  (testing "returns nil when there's no path"
    (let [the-file (file "the-filename.txt")
          the-path (path-of the-file)]
      (is (= nil the-path)))))

(deftest filter-on-extension-test
  (let [txt-a (file "a.txt")
        img-file (file "some/path/with/txt/in/it/txt.png")
        txt-b (file "b.txt")
        all-files [txt-a img-file txt-b]]

    (testing "filters on extension for matches with paths"
      (is (= [img-file] (filter-on-extension all-files "png"))))

    (testing "filters on extension for multiple matches"
      (is (= [txt-a txt-b] (filter-on-extension all-files "txt"))))

    (testing "returns empty list when no matches"
      (is (= [] (filter-on-extension all-files "unicorn"))))

    (testing "returns empty list when given no files"
      (is (= [] (filter-on-extension [] "txt"))))))