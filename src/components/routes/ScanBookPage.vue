<template>
    <div ref="hidable-layout">
        <div class="field has-text-justified">
            <label class="label">Search for an ISBN...</label>
            <div class="control">
                <input ref="searchTitle" class="input" type="text" placeholder="A fancy ISBN" @keypress="this.onPrompt()">
            </div>
        </div>
        <button class="button is-info" @click="this.startScan()">...Or scan an ISBN bar-code</button>
    </div>
</template>

<script>
import { BarcodeScanner } from '@capacitor-community/barcode-scanner';
export default {
    name:"Scan Book",
    components:
    { },
    data()
    {
    },
    methods: {
        onPrompt()
        {
            clearTimeout(this.searchTimer);
            this.searchTimer = setTimeout(() => {
                this.redirectToBook(this.getISBNPrompt());
                }, 666);
        },

        getISBNPrompt() {
            return this.$refs.searchTitle.value;
        },

        async startScan()
        {
            try {
                var isbn = await this.getISBN();
                this.redirectToBook(isbn);                            
            }
            catch (error) {
                alert(error);
            }
        },

        async redirectToBook(isbn)
        {
            var book = await this.getBookFromISBN(isbn);
                if(!book || book.error) 
                {
                    console.log("not found, redirecting");
                    this.$router.push({
                        name: 'NotFound',
                        params: {
                            message: `Book not found.`
                        }
                    });
                }
                else
                {
                    console.log(`found ${book.title}, redirecting to details.`);
                    this.$router.push({
                        name:'BookDetail',
                    params: {id: book.id}
                    });
                }
        },

        async getISBN()
        {
            BarcodeScanner.hideBackground(); // make background of WebView transparent
            var result = await BarcodeScanner.startScan(); // start scanning and wait for a result
            return result.content ?? "";
        },



        hideLayoutForScan()
        {
            this.$.refs.layout.display = "none";
        }
    }
}

</script>

