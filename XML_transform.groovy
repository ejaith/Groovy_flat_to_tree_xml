import groovy.xml.MarkupBuilder

def writer = new StringWriter()
def outXml = new MarkupBuilder(writer)

def body = new File( 'data/in/input1.xml')

def inXml = new XmlSlurper().parse(body)
def so = inXml.item.collect {it.salesorder}.unique{it}

outXml.SalesOrders {

    so.each { x  ->

        def salesOrder = inXml.item.findAll { item -> item.salesorder == x }

        'order' {

            salesOrder.eachWithIndex { y, i ->

                if (i == 0) {
                    'Header' {

                        'orderNumber'   y.salesorder
                        'customer'      y.customer
                        'date'          y.date
                    }
                }

                'Item' {
                    'li'            y.lineitem
                    'articleid'     y.articleid
                    'articledesc'   y.articledesc
                    'unitprice'     y.unitprice
                    'currency'      y.currency
                    'qty'           y.qty
                    'sloc'          y.sloc

                }

            }

        }
    }
}


writer.toString()