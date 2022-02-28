<template>
    <button class="button is-info" @click="this.startScan()">Scan an ISBN bar-code</button>
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
        async startScan()
        {
            try {
                var isbn = await this.getISBN();
                console.log(isbn);                
            }
            catch (error) {
                alert(error);
            }
        },

        async getISBN()
        {
            BarcodeScanner.hideBackground(); // make background of WebView transparent
            var result = await BarcodeScanner.startScan(); // start scanning and wait for a result
            return result.content ?? "";
        }
    }
}

</script>

