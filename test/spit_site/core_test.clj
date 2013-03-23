(ns spit-site.core-test
  (:use clojure.test
        spit-site.core
        [clojure.java.io :only [file]]))

(deftest template-file->tmpl-test
  (testing "separation of a file with a path to path + name"
    (let [root (file "test-root")
          template-file (file root "my/templates/my-template.tmpl")]
      (is (=
            {:name "my-template.tmpl" :path "my/templates"}
            (template-file->tmpl root template-file)))))

  (testing "separation of a root file to path + name"
    (let [root (file "/")
          template-file (file root "my-template.tmpl")]
      (is (=
            {:name "my-template.tmpl" :path nil}
            (template-file->tmpl root template-file))))))