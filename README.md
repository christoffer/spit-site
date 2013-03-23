# spit-site

A toy project of mine to learn [Clojure](http://clojure.org).

The idea is that this will be some sort of tool similar to [Jekyll](https://github.com/mojombo/jekyll) in that it takes dynamic data and generates a static site.

However the core concept of spit-site is the equation: `templates` + `data` = `web-site`.

Given the following directory structure:

```
root
 |__ templates
      |____ products
              |______index.mustache
      |____ about.mustache
 |__ data
      |_____products/index.json
```

Containing the files:

`templates/products/index.mustache`

```mustache
<h1>Product catalog {{year}}</h1>

<ul>
{{#products}}
  <li>{{name}} {{#price}} available at: ${{.}}{{/price}}</li>
{{#products}}
</ul>
```

`templates/about.mustache`

```mustache
<h1>About MyCompany</h1>

<p>Dedicated to you!</p>
```

`data/products.json`

```json
{
  "year": 2013,
  "products": [
    {
      "name": "Screwdriver",
      "price": 19.90
    },
    {
      "name": "Hammer"
    }
  ]
}
```

A site with two pages would be created: 

`example.com/about.html`:

```html
<h1>About MyCompany</h1>

<p>Dedicated to you!</p>
```

and `example.com/products/index.html`:

```html
<h1>Product catalog 2013</h1>

<ul>
  <li>Screwdriver available at: $19.90</li>
  <li>Hammer</li>
</ul>
```

## Usage

Probably something like: 

```
spit-site --templates ./my-templates --data ./my-data
```

(default values for the directories are: `./templates` and `./data`)

## Licenced under The MIT License (MIT)

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

([MIT Licence on opensource.org](http://opensource.org/licenses/MIT))

Copyright © 2013 Christoffer Klang
