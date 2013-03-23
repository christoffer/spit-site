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

(deftest has-extension?-test
  (let [text-file (file "poetry.txt")
        xml-file-with-path (file "some/path/Manifest.xml")
        extension-less (file "Procfile")]

    (testing "returns true when extension match"
      (is (= true (has-extension? "txt" text-file)))
      (is (= true (has-extension? "xml" xml-file-with-path))))

    (testing "returns false when extension does not match"
      (is (= false (has-extension? "png" text-file))))

    (testing "returns false for files that are missing an extension"
      (is (= false (has-extension? "" extension-less))))))