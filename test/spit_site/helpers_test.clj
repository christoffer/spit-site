(ns spit-site.helpers-test
  (:use clojure.test
        spit-site.helpers
        [clojure.java.io :only [file]]))

(deftest relativize-test
  (testing "strips out the base of the two dirs"
    (let [expected (file "a/b/c")
          actual (relativize (file "base/dir") (file "base/dir/a/b/c"))]
      (is (= expected actual))))

  (testing "returns nil when there is no common base"
    (is (= nil (relativize (file "a/b/c") (file "d/e/f"))))))

(deftest path-of-test
  (testing "returns the path component of a composite path"
    (let [the-file (file "the/path/the-filename.txt")
          the-path (path-of the-file)]
      (is (= "the/path" the-path))))

  (testing "returns nil when there's no path"
    (let [sole-file (file "the-filename.txt")
          root-file (file "/filename")]
      (is (= nil (path-of sole-file)))
      (is (= nil (path-of root-file))))))

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