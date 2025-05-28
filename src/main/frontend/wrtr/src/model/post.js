export default class Post {
    id;
    content;
    images;
    attachments;
    date;
    authorId;
    authorUsername;
    /**
     * @param {string} id Post id
     * @param {string} content Post content
     * @param {string[]} images Post images
     * @param {string[]} attachments Post attachments 
     * @param {Date} date When the post was made
     * @param {string} authorId Id of the post's author
     * @param {string} authorUsername Username of the post's author
     */
    constructor(id, content, images, attachments, date, authorId, authorUsername) {
        this.id = id;
        this.content = content;
        this.images = images;
        this.attachments = attachments;
        this.date = date;
        this.authorId = authorId;
        this.authorUsername = authorUsername;
    }

    /**
     * @param {any} data JSON data
     * @returns {Post[]} Posts generated from JSON data
     */
    static fromJson(data) {
        if (data.length === 0 || data === undefined) {
            return;
        }
        return data.map((post) => {
            let date = new Date(...post["date"].splice(0, 6));
            date.setMonth(date.getMonth() - 1);
            let images = post["resourceSet"].filter((res) => {
                const extension = res.path.split(".").at(-1);
                return ["jpg", "jpeg", "png", "avif", "gif", "svg", "webp", "bmp"].indexOf(extension) !== -1;
            });
            let attachments = post["resourceSet"].filter((res) => {
                const extension = res.path.split(".").at(-1);
                return ["jpg", "jpeg", "png", "avif", "gif", "svg", "webp", "bmp"].indexOf(extension) === -1;
            });
            return new Post(post["postId"], post["content"], images, attachments,
                date, post["authorId"], post["authorUsername"]);
        });
    }
}
