export default class User {
    id;
    username;
    bio;
    pfpPath;
    role;
    /**
     * @param {string} id User ID
     * @param {string} username Username
     * @param {string} bio User's bio
     * @param {string} pfpPath Path to the profile picture of the account
     * @param {string} role Role of the user
     */
    constructor(id, username, bio, pfpPath, role) {
        this.id = id;
        this.username = username;
        this.bio = bio;
        this.pfpPath = pfpPath;
        this.role = role;
    }

    /**
     * @param {any} data JSON data
     * @returns {User} User generated from JSON data 
     */
    static fromJson(data) {
        return new User(data["id"], data["username"], 
            data["bio"], data["profilePicture"], data["role"]);
    }
}
